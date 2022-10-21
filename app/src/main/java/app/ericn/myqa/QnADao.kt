package app.ericn.myqa

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE

@Dao
interface QnADao {
    @Insert(onConflict = REPLACE)
    fun insertQuestions(questions: QuestionEntity)
}