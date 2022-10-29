package app.ericn.myqa

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.room.Room
import app.ericn.myqa.databinding.ActivityMainBinding
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

class MainActivity : AppCompatActivity() {
    private val tag = "MainActivity"
    private val adapter: QnaListAdapter = QnaListAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)
        binding.qaList.adapter = adapter

        fetchAndRender()
    }

    private fun fetchAndRender() {
        val db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "database-name"
        ).build()
        val dao = db.dao()

        lifecycleScope.launch(Dispatchers.Main) {
            withContext(Dispatchers.Main) {
                var startTime: Long = 0
                dao.readMcqs()
                    .flatMapConcat { map ->
                        /*
                        If no data, populate first
                         */
                        if (map.isEmpty()) {
                            return@flatMapConcat flow<Unit> {
                                dao.insertQuestions(dummyQuestions)
                                dao.insertOptions(dummyOptions)
                                dao.insertAnswers(dummyAnswers)
                            }
                        } else {
                            return@flatMapConcat flowOf(Unit)
                        }
                    }
                    .flatMapConcat {
                        startTime = System.currentTimeMillis()
                        return@flatMapConcat dao.readMcqAnswers1()
                    }
                    .flowOn(Dispatchers.IO)
                    .collect(FlowCollector { map ->
                        val end = System.currentTimeMillis()
                        Log.d(tag, "time taken to fetch Q&A from DB: ${end- startTime}ms")
                        val items = map.mapNotNull {
                            if (it.options.isNotEmpty()) {
                                ListUiItem.MultiChoice(it.question, it.answers, it.options)
                            } else {
                                null
                            }
                        }.toList()
                        adapter.submitList(items)
                    })
            }
        }
    }
}
