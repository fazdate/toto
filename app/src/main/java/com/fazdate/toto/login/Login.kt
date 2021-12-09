package com.fazdate.toto.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.widget.Toast
import android.os.Bundle
import com.fazdate.toto.R
import com.fazdate.toto.mainMenu.MainMenu
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.login.*

/*
X - ConstraintLayout használata (1p)
X - DataBinding használata (1p)
X - Fragmentek használata (1p)
 - SafeArgs Plugin használata a Fragmentek közti navigációhoz (1p)
X - ViewModel használata (1p)
X - LiveData backing propertyvel (1p)
 - LiveData transformation használata (1p)
 - RoomDatabase használata (1p)
X - Coroutine-ok használata az adatbázisműveletekez (1p)
X - RecyclerView használata (1p)
 - RecyclerView DiffUtil használata (1p)
X - RecyclerView ClickListener helyes használata (1p)
 - RecyclerView Header helyes használata (1p)
X - Webes szolgáltatástól történő adat elkérése és megjelenítése (1p)
X - BÓNUSZ PONT: Repository pattern használata (Réteg a viewmodel és a database - internet közé) (+1p)
 */

class Login : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login)

        auth = FirebaseAuth.getInstance()

        loginbtn.setOnClickListener {
            auth.signInWithEmailAndPassword(email.text.toString(), password.text.toString()).addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val intent = Intent(this, MainMenu::class.java)
                    startActivity(intent)
                }  else {
                    Toast.makeText( this, "LOGIN FAILED", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onBackPressed() {
        finishAffinity()
    }
}
