package cz.sazel.sqldelight.node.sqlite3

import app.cash.sqldelight.Query
import app.cash.sqldelight.Transacter
import app.cash.sqldelight.db.QueryResult
import app.cash.sqldelight.db.SqlCursor
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.db.SqlPreparedStatement
import node.sqlite3.Sqlite3

class SQLite3Driver : SqlDriver {
    private val listeners = mutableMapOf<String, MutableSet<Query.Listener>>()
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
        TODO("Not yet implemented")
    }

    override fun removeListener(listener: Query.Listener, queryKeys: Array<String>) {
        TODO("Not yet implemented")
    }

    override fun notifyListeners(queryKeys: Array<String>) {
        TODO("Not yet implemented")
    }

    override fun close() {
        TODO("Not yet implemented")
    }

    private fun Sqlite3.Statement.bind(parameters: Int, binders: (SqlPreparedStatement.() -> Unit)?) = binders?.let {
        if (parameters > 0) {
            val bound = SQLite3PreparedStatement(parameters)
            binders(bound)
            bind(bound.parameters)
        }
    }
}
