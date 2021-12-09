package com.fazdate.toto.results

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.fazdate.toto.R
import com.fazdate.toto.firebase.FirebaseMethods
import com.fazdate.toto.game.Game
import com.fazdate.toto.mainMenu.MainMenu
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.results.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class Results : AppCompatActivity(), ResultAdapter.OnItemClickListener, CoroutineScope {

    private var providedOutcomes = ArrayList<ResultsModel>()
    private var correctOutcomes = ArrayList<ResultsModel>()
    private lateinit var resultAdapter :ResultAdapter
    private var job: Job = Job()
    override val coroutineContext: CoroutineContext get() = Dispatchers.Main + job

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.results)

        providedOutcomes = intent.getSerializableExtra("list") as ArrayList<ResultsModel>

        launch {
            populateRecyclerView()
            uploadResultsToDB(providedOutcomes, correctOutcomes)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
    }

    private fun populateRecyclerView() {
        correctOutcomes = createCorrectOutcomes()
        resultAdapter = ResultAdapter(providedOutcomes, correctOutcomes, this)
        resultsRecyclerView.adapter = resultAdapter
        resultsRecyclerView.layoutManager = LinearLayoutManager(this)
        resultsRecyclerView.setHasFixedSize(true)

        newGameBtn.setOnClickListener {
            val intent = Intent(this, Game::class.java)
            startActivity(intent)
        }

        mainMenuBtn.setOnClickListener {
            val intent = Intent(this, MainMenu::class.java)
            startActivity(intent)
        }
    }

    override fun onItemClick(position: Int) {
        if (providedOutcomes[position] == correctOutcomes[position]) {
            Toast.makeText(this, "Your guess was correct", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Your guess was incorrect", Toast.LENGTH_SHORT).show()

        }
    }

    private fun createCorrectOutcomes(): ArrayList<ResultsModel> {
        val list = ArrayList<ResultsModel>()
        for (i in 0..14) {
            when((0..2).random()) {
                0 -> list.add(ResultsModel("x"))
                1 -> list.add(ResultsModel("1"))
                2 -> list.add(ResultsModel("2"))
            }
        }
        return list
    }

    private suspend fun uploadResultsToDB(providedList: ArrayList<ResultsModel>, correctList: ArrayList<ResultsModel>) {
        var correctGuesses = 0
        for (i in 0..13) {
            if (providedList[i] == correctList[i]) {
                correctGuesses++
            }
        }
        val email = FirebaseAuth.getInstance().currentUser?.email.toString()

        val prizeMoney = (correctGuesses * 1000)
        val toastMsg = "You won $$prizeMoney!"
        val gameNumber = FirebaseMethods().generateGameID(email)
        Toast.makeText(this, toastMsg, Toast.LENGTH_SHORT).show()

        val gameData = hashMapOf(
            "gameNumber" to gameNumber,
            "numberOfCorrectGuesses" to correctGuesses,
            "prizeMoney" to prizeMoney
        )

        FirebaseMethods().addDocumentToCollection(email, gameData)
    }
}