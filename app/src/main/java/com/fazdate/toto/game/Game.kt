package com.fazdate.toto.game

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.fazdate.toto.R
import com.fazdate.toto.results.ResultsModel
import com.fazdate.toto.results.Results
import kotlinx.android.synthetic.main.game.*
import java.util.ArrayList
import kotlin.properties.Delegates

class Game : AppCompatActivity() {

    private val listOfOutcomes : ArrayList<ResultsModel> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.game)

        var matchCounter by Delegates.observable(1) { _, _, newValue ->
            if (newValue == 15) {
                finishedGame()
            } else {
                match_count.text = "Match #$newValue"
                team_1.text = "Team #" + (newValue * 2 - 1)
                team_2.text = "Team #" + (newValue * 2)
            }
        }

        button_1.setOnClickListener {
            listOfOutcomes.add(ResultsModel("1"))
            matchCounter++
        }

        button_x.setOnClickListener {
            listOfOutcomes.add(ResultsModel("x"))
            matchCounter++
        }

        button_2.setOnClickListener {
            listOfOutcomes.add(ResultsModel("2"))
            matchCounter++
        }

    }

    private fun finishedGame() {
        val intent = Intent(this, Results::class.java)
        intent.putExtra("list", listOfOutcomes)
        startActivity(intent)
    }

}