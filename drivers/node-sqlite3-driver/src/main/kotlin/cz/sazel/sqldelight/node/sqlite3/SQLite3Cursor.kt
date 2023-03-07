package cz.sazel.sqldelight.node.sqlite3

import app.cash.sqldelight.db.SqlCursor
import node.sqlite3.Sqlite3
import org.khronos.webgl.Int8Array
import org.khronos.webgl.Uint8Array

internal class SQLite3Cursor(private val statement: Sqlite3.Statement) : SqlCursor {
    /*
        override fun next(): Boolean = statement.step()
        override fun getString(index: Int): String? = statement.get()[index]
        override fun getLong(index: Int): Long? = (statement.get()[index] as? Double)?.toLong()
        override fun getBytes(index: Int): ByteArray? = (statement.get()[index] as? Uint8Array)?.let {
            Int8Array(it.buffer).unsafeCast<ByteArray>()
        }
        override fun getDouble(index: Int): Double? = statement.get()[index]

        override fun getBoolean(index: Int): Boolean? {
            val double = (statement.get()[index] as? Double)
            return if (double == null) null
            else double.toLong() == 1L
        }

        fun close() { statement.freemem() }
      */

    override fun next(): Boolean = statement

    override fun getString(index: Int): String? {
        TODO("Not yet implemented")
    }

    override fun getLong(index: Int): Long? {
        TODO("Not yet implemented")
    }

    override fun getBytes(index: Int): ByteArray? {
        TODO("Not yet implemented")
    }

    override fun getDouble(index: Int): Double? {
        TODO("Not yet implemented")
    }

    override fun getBoolean(index: Int): Boolean? {
        TODO("Not yet implemented")
    }
}
