package cz.sazel.sqldelight.node.sqlite3

import app.cash.sqldelight.Query
import app.cash.sqldelight.Transacter
import app.cash.sqldelight.db.*
import events.cz.sazel.sqldelight.node.sqlite3.SQLite3Exception
import node.sqlite3.Sqlite3
import node.sqlite3.Sqlite3.OPEN_CREATE
import node.sqlite3.Sqlite3.OPEN_READWRITE
import kotlin.coroutines.Continuation
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine


fun initSqlite3Driver(
    filename: String, mode: Number = OPEN_CREATE.toInt() or OPEN_READWRITE.toInt(),
    schema: SqlSchema? = null,
): SqlDriver = SQLite3Driver(Sqlite3.Database(filename, mode))

class SQLite3Driver(private val db: Sqlite3.Database) : SqlDriver {
    private val listeners = mutableMapOf<String, MutableSet<Query.Listener>>()
    private val statements = mutableMapOf<Int, Sqlite3.Statement>()
    private var transaction: Transacter.Transaction? = null

    private inner class Transaction(
        override val enclosingTransaction: Transacter.Transaction?,
    ) : Transacter.Transaction() {
        override fun endTransaction(successful: Boolean): QueryResult<Unit> = QueryResult.AsyncValue {
            if (enclosingTransaction == null) {
                if (successful) {
                    //worker.run("END TRANSACTION")
                } else {
                    //worker.run("ROLLBACK TRANSACTION")
                }
            }
            transaction = enclosingTransaction
        }
    }

    private suspend fun createOrGetStatement(identifier: Int?, sql: String): Sqlite3.Statement {
        val continuation: (Continuation<Sqlite3.Statement>) -> Unit = { cont ->
            db.prepare(sql, callback = { self: Any, _ ->
                if (self !is Error) cont.resume(self as Sqlite3.Statement) else cont.resumeWithException(SQLite3Exception(self))
            })
        }
        return if (identifier == null) {
            suspendCoroutine(continuation)
        } else {
            statements.getOrPut(identifier) { suspendCoroutine(continuation) }.apply { reset() }
        }
    }

    override fun <R> executeQuery(identifier: Int?, sql: String, mapper: (SqlCursor) -> R, parameters: Int, binders: (SqlPreparedStatement.() -> Unit)?): QueryResult<R> {
        TODO("Not yet implemented")
    }

    override fun execute(identifier: Int?, sql: String, parameters: Int, binders: (SqlPreparedStatement.() -> Unit)?): QueryResult<Long> {
        TODO("Not yet implemented")
    }

    override fun newTransaction(): QueryResult<Transacter.Transaction> {
        TODO("Not yet implemented")
    }

    override fun currentTransaction(): Transacter.Transaction? {
        TODO("Not yet implemented")
    }

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

//    private fun createOrGetStatement(identifier: Int?, sql: String): Sqlite3.Statement = if (identifier == null) {
//        db.prepare(sql)
//    } else {
//        statements.getOrPut(identifier, { db.prepare(sql) }).apply { reset() }
//    }

    private fun Sqlite3.Statement.bind(parameters: Int, binders: (SqlPreparedStatement.() -> Unit)?) = binders?.let {
        if (parameters > 0) {
            val bound = SQLite3PreparedStatement(parameters)
            binders(bound)
            bind(bound.parameters)
        }
    }
}
