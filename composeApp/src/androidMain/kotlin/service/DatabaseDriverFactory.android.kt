package service

import android.content.Context
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import example.project.commonMain.cache.DatabaseSchema

actual class DatabaseDriverFactory(private val cxt: Context) {
    actual fun createDriver(): SqlDriver {
        return AndroidSqliteDriver(DatabaseSchema.Schema, cxt, "data.db")
    }
}