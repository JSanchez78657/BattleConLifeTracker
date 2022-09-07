package com.example.battleconlifetracker.layouts

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import com.example.battleconlifetracker.R
import com.example.battleconlifetracker.model.game.Game
import com.example.battleconlifetracker.model.game.GameSerializable

class SettingsMenu : AppCompatActivity() {

    private var game = Game()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if(intent?.extras?.getSerializable("Game") != null)
            game = Game(intent.extras!!.getSerializable("Game") as GameSerializable)
        setContentView(R.layout.settings_menu)
        findViewById<ImageButton>(R.id.BackButton).setOnClickListener {
            val intent = Intent(this, GameScreen::class.java)
            intent.putExtra("Game", game.getSerializable())
            startActivity(intent)
            finish()
        }
        findViewById<ImageButton>(R.id.ResetGameButton).setOnClickListener {
            intent.putExtra("Game", game.getSerializable())
            val intent = Intent(this, GameScreen::class.java)
            startActivity(intent)
            finish()
        }
        findViewById<ImageButton>(R.id.TeamsButton).setOnClickListener {

        }
    }

    override fun onBackPressed() {
//        super.onBackPressed()
//        val intent = Intent(this, GameScreen::class.java)
//        intent.putExtra("Game", game)
//        startActivity(intent)
//        finish()
    }
}
