package app.ericn.myqa

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface QnADao {
    @Insert(onConflict = REPLACE)
    fun insertQuestions(data: List<QuestionEntity>): List<Long>
    @Insert(onConflict = REPLACE)
    fun insertOptions(data: List<OptionEntity>): List<Long>
    @Insert(onConflict = REPLACE)
    fun insertAnswers(data: List<AnswerEntity>): List<Long>

    @Query("SELECT question.question_text as question, answer.answer_text as answer FROM question, answer WHERE question.question_id = answer.question_id AND answer.option_id = ''")
    fun readTextAnswers(): Flow<List<TextAnswer>>

    @Query("SELECT * from question INNER JOIN option ON option.question_id = question.question_id")
    fun readMcqs(): Flow<Map<QuestionEntity, List<OptionEntity>>>

    /**
     * Classic approach
     */
    @Query("SELECT * FROM question")
    fun readMcqAnswers1(): Flow<List<QuestionWithRelations>>

    /**
     * Multi-map #1
     * Doesn't even compile
     * "Not sure how to convert a Cursor to this method's return type"
     */
//    @Query("SELECT * FROM (SELECT * from question INNER JOIN option ON option.question_id = question.question_id) AS q INNER JOIN answer ON answer.question_id = q.question_id")
//    fun readMcqAnswers2(): Flow<Map<Map<QuestionEntity, List<OptionEntity>>, List<AnswerEntity>>>

    /**
     * Multi-map #2
     * Doesn't even compile
     * "Not sure how to convert a Cursor to this method's return type"
     */
//    @Query("SELECT * FROM question INNER JOIN (SELECT * FROM option INNER JOIN answer ON answer.option_id = option.option_id) as a ON a.question_id = question.question_id")
//    fun readMcqAnswers3(): Flow<Map<QuestionEntity, Map<OptionEntity, AnswerEntity>>>
}

val dummyQuestions = listOf(
    QuestionEntity("1", "Relationships"),
    QuestionEntity("2", "Priorities"),
    QuestionEntity("3", "What is your favourite food?"),
    QuestionEntity("4", "What's your favourite video game?"),
    QuestionEntity("5", "Do you want kids?"),
)

val dummyOptions = listOf(
    OptionEntity("1", "1", "Communication", "communication"),
    OptionEntity("2", "1", "Compassion", "compassion"),
    OptionEntity("3", "1", "Collaboration", "collaboration"),
    OptionEntity("4", "1", "Commitment", "commitment"),
    OptionEntity("5", "2", "Career", "career"),
    OptionEntity("6", "2", "Communities", "community"),
    OptionEntity("7", "2", "Education", "education"),
    OptionEntity("8", "2", "Environment", "environment"),
    OptionEntity("9", "5", "Want kids", "want_kids"),
    OptionEntity("10", "5", "Don't want kids", "no_kids"),
    OptionEntity("11", "5", "Open to kids", "open_to_kids"),
)

val dummyAnswers = listOf(
    AnswerEntity("1", "3", "1", 123L, textValue = null),
    AnswerEntity("3", "2", "1", 123L, textValue = null),
    AnswerEntity("4", "2", "2", 123L, textValue = null),
    AnswerEntity("5", optionId = "", "3", 123L, textValue = "pizza"),
    AnswerEntity("6", optionId = "", "4", 123L, textValue = "gta"),
    AnswerEntity("7", optionId = "10", "5", 123L, textValue = null),
)