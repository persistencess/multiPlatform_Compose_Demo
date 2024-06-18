package service

import example.project.commonMain.cache.Word
import ui.types.PageModel

class StoreService {
    fun selectAll(): List<Word> = databaseSchema.sqlQueries.selectWords().executeAsList()
    fun insert(q: PageModel) {
        databaseSchema.sqlQueries.insertWord(
            content = q.content,
            from = q.author,
            date = q.date,
            weekday = q.week,
            imageUrl = q.imgUrl
        )
    }
}