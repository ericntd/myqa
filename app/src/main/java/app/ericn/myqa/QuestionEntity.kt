package app.ericn.myqa

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "question")
data class QuestionEntity(
    @PrimaryKey @ColumnInfo(name = "question_id") val id: String,
    @ColumnInfo(name = "question_text") val text: String
)