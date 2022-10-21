package app.ericn.myqa

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [
        QuestionEntity::class,
        OptionEntity::class,
        AnswerEntity::class,
    ],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun dao(): QnADao
}