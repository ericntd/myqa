package app.ericn.myqa

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
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

        lifecycleScope.launch(Dispatchers.IO) {
            dao.insertQuestions(dummyQuestions)
            dao.insertOptions(dummyOptions)
            dao.insertAnswers(dummyAnswers)
            val async = async {
                Log.e("MainActivity", "testing")
            }
        }
    }
}
