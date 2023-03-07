package cz.sazel.sqldelight.node.sqlite3

import app.cash.sqldelight.Query
import app.cash.sqldelight.Transacter
import app.cash.sqldelight.db.*
import node.sqlite3.Sqlite3
import node.sqlite3.Sqlite3.OPEN_CREATE
import node.sqlite3.Sqlite3.OPEN_READWRITE
import kotlin.coroutines.Continuation
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

suspend fun initSqlite3SqlDriver(
    filename: String, mode: Number = OPEN_CREATE.toInt() or OPEN_READWRITE.toInt(),
    schema: SqlSchema? = null,
): SqlDriver = SQLite3Driver(initSqlite3Database(filename, mode)).withSchema(schema)

private suspend fun initSqlite3Database(
    filename: String, mode: Number = OPEN_CREATE.toInt() or OPEN_READWRITE.toInt()
): Sqlite3.Database =
    suspendCoroutine { cont ->
        var result: Result<Unit>? = null
        val db = Sqlite3.Database(filename, mode, callback = {
            result = if (it == null) Result.success(Unit) else Result.failure(it)
        })
        while (result == null) {
            // expected
        }
        result?.let {
            if (it.isSuccess) cont.resume(db)
            else cont.resumeWithException(SQLite3Exception(it.exceptionOrNull()))
        }
    }

suspend fun SqlDriver.withSchema(schema: SqlSchema? = null): SqlDriver = this.also { schema?.create(it)?.await() }

class SQLite3Driver(private val db: Sqlite3.Database) : SqlDriver {
    private val listeners = mutableMapOf<String, MutableSet<Query.Listener>>()
    private val statements = mutableMapOf<Int, Sqlite3.Statement>()
    private var transaction: Transacter.Transaction? = null

    private inner class Transaction(
        override val enclosingTransaction: Transacter.Transaction?,
    ) : Transacter.Transaction() {
        override fun endTransaction(successful: Boolean): QueryResult<Unit> = QueryResult.AsyncValue {
            if (enclosingTransaction == null) {
                val sql = if (successful) "END TRANSACTION" else "ROLLBACK TRANSACTION"
                suspendCoroutine { cont ->
                    val callback: (Any) -> Unit = { self ->
                        if (self !is Throwable) {
                            cont.resume(self as Sqlite3.Statement)
                        } else {
                            cont.resumeWithException(SQLite3Exception(self))
                        }
                    }
                    db.run(sql, callback)
                }
            }
            transaction = enclosingTransaction
        }
    }

    private suspend fun createOrGetStatement(identifier: Int?, sql: String): Sqlite3.Statement {
        val continuation: (Continuation<Sqlite3.Statement>) -> Unit = { cont ->

            val callback: (Any) -> Unit = { self ->
                println("prepare() $self ${self::class.simpleName}")
                if (self !is Throwable) {
                    println("prepare resume ok")
                    cont.resume(self as Sqlite3.Statement)
                } else {
                    println("prepare resume with exception $self")
                    cont.resumeWithException(SQLite3Exception(self))
                }
            }
            db.prepare(sql, null, callback)
            println("After prepare")
        }
        return if (identifier == null) {
            suspendCoroutine(continuation)
        } else {
            statements.getOrPut(identifier) { suspendCoroutine(continuation) }.apply { reset() }
        }
    }

    override fun <R> executeQuery(identifier: Int?, sql: String, mapper: (SqlCursor) -> R, parameters: Int, binders: (SqlPreparedStatement.() -> Unit)?): QueryResult<R> =
        QueryResult.AsyncValue {
            val statement = createOrGetStatement(identifier, sql)
            statement.bind(parameters, binders)
            val cursor = SQLite3Cursor(statement)
            mapper(cursor)
        }

    override fun execute(identifier: Int?, sql: String, parameters: Int, binders: (SqlPreparedStatement.() -> Unit)?): QueryResult<Long> = QueryResult.AsyncValue {
        val statement = createOrGetStatement(identifier, sql)
        statement.bind(parameters, binders)
        statement.run()
        return@AsyncValue 0
    }

    override fun newTransaction(): QueryResult<Transacter.Transaction> = QueryResult.AsyncValue {
        val enclosing = transaction
        val transaction = Transaction(enclosing)
        this.transaction = transaction
        if (enclosing == null) {
            suspendCoroutine { cont ->
                val callback: (Any) -> Unit = { self ->
                    if (self !is Throwable) {
                        cont.resume(self as Sqlite3.Statement)
                    } else {
                        cont.resumeWithException(SQLite3Exception(self))
                    }
                }
                db.run("BEGIN TRANSACTION", callback)
            }
        }

        return@AsyncValue transaction
    }

    override fun currentTransaction(): Transacter.Transaction? = transaction

    override fun addListener(listener: Query.Listener, queryKeys: Array<String>) {
        queryKeys.forEach {
            listeners.getOrPut(it) { mutableSetOf() }.add(listener)
        }
    }

    override fun removeListener(listener: Query.Listener, queryKeys: Array<String>) {
        queryKeys.forEach {
            listeners[it]?.remove(listener)
        }
    }

    override fun notifyListeners(queryKeys: Array<String>) {
        queryKeys.flatMap { listeners[it].orEmpty() }
            .distinct()
            .forEach(Query.Listener::queryResultsChanged)
    }

    override fun close() {
        db.close {
            println(it)
        }
    }

    private fun Sqlite3.Statement.bind(parameters: Int, binders: (SqlPreparedStatement.() -> Unit)?) = binders?.let {
        if (parameters > 0) {
            val bound = SQLite3PreparedStatement(parameters)
            binders(bound)
            bind(bound.parameters)
        }
    }
}
