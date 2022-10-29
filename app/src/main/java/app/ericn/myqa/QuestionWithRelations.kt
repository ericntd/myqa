package app.ericn.myqa

import androidx.room.Embedded
import androidx.room.Relation

data class QuestionWithRelations(
    @Embedded
    val question: QuestionEntity,
    @Relation(
        parentColumn = "question_id",
        entityColumn = "question_id"
    )
    val options: List<OptionEntity>,
    @Relation(
        parentColumn = "question_id",
        entityColumn = "question_id"
    )
    val answers: List<AnswerEntity>
)