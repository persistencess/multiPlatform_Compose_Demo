package service

import app.cash.sqldelight.db.SqlDriver
import example.project.commonMain.cache.DatabaseSchema

expect class DatabaseDriverFactory {
    fun createDriver():SqlDriver
}
lateinit var databaseSchema:DatabaseSchema