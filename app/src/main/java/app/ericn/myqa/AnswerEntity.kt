package app.ericn.myqa

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(
    tableName = "answer",
    primaryKeys = ["option_id", "profile_id", "question_id"]
)
data class AnswerEntity(
    @ColumnInfo(name = "id") val id: String,
    /**
     * Only Applicable to choice-based questions
     */
    @ColumnInfo(name = "option_id") val optionId: String,
    @ColumnInfo(name = "question_id") val questionId: String,
    @ColumnInfo(name = "profile_id") val profileId: Long,
    /**
     * Applicable to text-based questions
     */
    @ColumnInfo(name = "text_value") val textValue: String?
)