package app.ericn.myqa

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Room
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        populateData()
    }

    private fun populateData() {
        val db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "database-name"
        ).build()
        val dao = db.dao()
        val deferred = runBlocking {
            withContext(Dispatchers.IO) {
                dao.insertQuestions(dummyQuestions[1])
            }
        }
    }
}
