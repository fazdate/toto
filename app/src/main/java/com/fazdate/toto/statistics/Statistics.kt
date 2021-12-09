package com.fazdate.toto.statistics

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.fazdate.toto.R
import com.fazdate.toto.firebase.FirebaseMethods
import com.fazdate.toto.game.Game
import com.fazdate.toto.mainMenu.MainMenu
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.results.newGameBtn
import kotlinx.android.synthetic.main.statistics.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class Statistics : AppCompatActivity(), CoroutineScope {

    private var job: Job = Job()
    override val coroutineContext: CoroutineContext get() = Dispatchers.Main + job
    private lateinit var adapter : StatisticsAdapter

    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.statistics)

        launch {
            val email = FirebaseAuth.getInstance().currentUser?.email.toString()
            populateStatistics(email)
            populateRecyclerView(email)
        }
    }

    private suspend fun populateRecyclerView(collectionName : String) {
        val list = FirebaseMethods().getEveryDocumentFromCollection(collectionName)?.documents
        val gameObjectList : ArrayList<GameObject> = ArrayList()
        if (list != null) {
            for (document in list) {
                val numberOfGames = document.get("gameNumber").toString().toInt()
                val prizeMoney = document.get("prizeMoney").toString().toInt()
                val numberOfCorrectGuesses = document.get("numberOfCorrectGuesses").toString().toInt()
                gameObjectList.add(GameObject(numberOfGames, numberOfCorrectGuesses, prizeMoney))
            }
        }

        adapter = StatisticsAdapter(gameObjectList)
        statisticsRecyclerView.adapter = adapter
        statisticsRecyclerView.layoutManager = LinearLayoutManager(this)
        statisticsRecyclerView.setHasFixedSize(true)

        mainMenuButton.setOnClickListener {
            val intent = Intent(this, MainMenu::class.java)
            startActivity(intent)
        }

        newGameBtn.setOnClickListener {
            val intent = Intent(this, Game::class.java)
            startActivity(intent)
        }
    }

    private suspend fun populateStatistics(collectionName : String) {
        val tPrize = FirebaseMethods().getTotalPrize(collectionName)
        val tAmountOfGames = FirebaseMethods().getEveryDocumentFromCollection(collectionName)?.size()
        val tCorrectGuesses = FirebaseMethods().getTotalCorrectGuesses(collectionName)
        val cGuessRatio = ((tCorrectGuesses.toDouble() / (tAmountOfGames?.times(14)!!)) * 100).toInt()

        val viewModel: StatisticsViewModel = ViewModelProvider(this)[StatisticsViewModel::class.java]
        viewModel.setTotalPrize(tPrize)
        viewModel.setTotalAmountOfGames(tAmountOfGames)
        viewModel.setTotalCorrectGuesses(tCorrectGuesses)
        viewModel.setCorrectGuessRatio(cGuessRatio)

        totalPrize.text = "$" + viewModel.totalPrize.value.toString()
        totalGames.text = viewModel.totalAmountOfGames.value.toString()
        totalCorrectGuess.text = viewModel.totalCorrectGuesses.value.toString()
        correctGuessRatio.text = viewModel.correctGuessRatio.value.toString() + "%"
    }
}