package app.ericn.myqa

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "question")
data class QuestionEntity(
    @PrimaryKey val id: Long,
    val text: String
)

val dummyQuestions = listOf(
    QuestionEntity(1L, "Relationships"),
    QuestionEntity(2L, "Priorities"),
    QuestionEntity(3L, "Ideals"),
    QuestionEntity(4L, "Causes"),
)