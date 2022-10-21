package app.ericn.myqa

import android.util.Log
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE

@Dao
interface QnADao {
    @Insert(onConflict = REPLACE)
    fun insertQuestions(questions: QuestionEntity)

    fun insertQuestionsPublic(questions: QuestionEntity) {
        Log.w("QnADao", "insertQuestionsPublic")
        insertQuestions(questions)
    }
}