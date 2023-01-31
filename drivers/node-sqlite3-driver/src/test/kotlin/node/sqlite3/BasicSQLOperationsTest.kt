@file:OptIn(ExperimentalCoroutinesApi::class)

package node.sqlite3

import node.sqlite3.Sqlite3
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import node.sqlite3.Sqlite3.OPEN_CREATE
import node.sqlite3.Sqlite3.OPEN_READWRITE
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine
import kotlin.test.Test

class BasicSQLOperationsTest {


    @Test
    fun testCreateDb() = runTest {
        val db: Sqlite3.Database = Sqlite3.Database("test.db", mode = OPEN_CREATE.toInt() or OPEN_READWRITE.toInt())
        val res = suspendCoroutine { cont ->
            db.run(
                """CREATE TABLE contacts (
                contact_id INTEGER PRIMARY KEY,
                first_name TEXT NOT NULL,
                last_name TEXT NOT NULL,
                email TEXT NOT NULL UNIQUE,
                phone TEXT NOT NULL UNIQUE);""", js("{}") as Any
            ) { self, err ->
                err?.let { cont.resumeWithException(err) } ?: cont.resume(self)
            };
        }
        val statement = suspendCoroutine { cont ->
            db.run(
                "INSERT INTO contacts (contact_id,first_name,last_name,email,phone) " +
                        "VALUES (?,?,?,?,?)", js("[1, \"Petr\", \"Novak\", \"petr.novak@gmail.com\", \"1234\"]")
            ) { self, err ->
                err?.let { cont.resumeWithException(err) } ?: cont.resume(self)
            }
        }
        val statement2 = suspendCoroutine { cont ->
            db.run(
                "INSERT INTO contacts (contact_id,first_name,last_name,email,phone) " +
                        "VALUES (?,?,?,?,?)", listOf(2, "Pavel", "Novotny", "pavel.novotny@gmail.com", "5674").toTypedArray()
            ) { self, err ->
                err?.let { cont.resumeWithException(err) } ?: cont.resume(self)
            }
        }

        suspendCoroutine { cont ->
            db.close {
                it?.let {
                    cont.resumeWithException(it)
                } ?: cont.resume(Unit)
            }
        }
    }


}
