package app.ericn.myqa

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.room.Room
import app.ericn.myqa.databinding.ActivityMainBinding
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

class MainActivity : AppCompatActivity() {
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
            // then render
            withContext(Dispatchers.Main) {
                dao.readMcqs()
                    .flowOn(Dispatchers.IO)
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
                        return@flatMapConcat dao.readMcqs()
                    }
                    .collect(FlowCollector { map ->
                        println("success")
                    })
            }
        }
    }
}
