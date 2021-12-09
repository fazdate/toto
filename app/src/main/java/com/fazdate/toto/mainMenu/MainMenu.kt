package com.fazdate.toto.mainMenu

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.fazdate.toto.R
import com.fazdate.toto.game.Game
import com.fazdate.toto.login.Login
import com.fazdate.toto.statistics.Statistics
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.mainmenu.*

class MainMenu : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.mainmenu)

        startGameBtn.setOnClickListener {
            val intent = Intent(this, Game::class.java)
            startActivity(intent)
        }

        statisticsBtn.setOnClickListener {
            val intent = Intent(this, Statistics::class.java)
            startActivity(intent)
        }

        signOutBtn.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            val intent = Intent(this, Login::class.java)
            startActivity(intent)
        }
    }

    // Pressing the back button should do nothing
    override fun onBackPressed() { }
}