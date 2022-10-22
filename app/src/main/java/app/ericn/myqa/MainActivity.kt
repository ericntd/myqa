package app.ericn.myqa

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.room.Room
import app.ericn.myqa.databinding.ActivityMainBinding
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.observeOn

class MainActivity : AppCompatActivity() {
    private val adapter: QnaListAdapter = QnaListAdapter()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)
        binding.qaList.adapter = adapter

        populateData()
    }

    private fun populateData() {
        val db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "database-name"
        ).build()
        val dao = db.dao()

        lifecycleScope.launch(Dispatchers.Main) {
            val populateJob = async(Dispatchers.IO) {
                dao.insertQuestions(dummyQuestions)
                dao.insertOptions(dummyOptions)
                dao.insertAnswers(dummyAnswers)
            }
            // make sure we finish populating db first
            populateJob.await()
            // then render
            withContext(Dispatchers.Main) {
                dao.readTextAnswers()
                    .flowOn(Dispatchers.IO)
                    .collect(FlowCollector { textAnswers ->
                        val list = textAnswers.map {
                            ListUiItem.Text(it.question, it.answer?: "")
                        }
                        adapter.submitList(list)
                    })
            }
        }
    }
}
