package cz.sazel.sqldelight.node.sqlite3

import app.cash.sqldelight.db.SqlCursor
import org.khronos.webgl.Int8Array
import org.khronos.webgl.Uint8Array

internal class SQLite3Cursor(private val rows: Array<Array<dynamic>>) : SqlCursor {

    private var row = -1;

    override fun next(): Boolean =
        if (row >= rows.size - 1)
            false
        else {
            row++
            true
        }

    private fun checkCursorState() {
        if (row < 0) throw SQLite3Exception("Cursor was not yet iterated, call next() first.")
    }

    override fun getString(index: Int): String? {
        checkCursorState()
        return rows[row][index] as String?
    }

    override fun getLong(index: Int): Long? {
        checkCursorState()

        return rows[row][index] as Long?
    }

    override fun getBytes(index: Int): ByteArray? {
        checkCursorState()
        return (rows[row][index] as? Uint8Array)?.let { Int8Array(it.buffer).unsafeCast<ByteArray>() }
    }

    override fun getDouble(index: Int): Double? {
        checkCursorState()
        return rows[row][index] as Double?
    }

    override fun getBoolean(index: Int): Boolean? {
        checkCursorState()
        return rows[row][index] as Boolean?
    }
}
