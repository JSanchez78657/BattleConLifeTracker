package com.example.battleconlifetracker.layouts

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import com.example.battleconlifetracker.R
import com.example.battleconlifetracker.model.game.Game
import com.example.battleconlifetracker.model.GameSettings

class SettingsMenu : AppCompatActivity() {

    private var game = Game(GameSettings(20, 2, 45, 2))

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if(intent?.extras?.getSerializable("Game") != null)
            game = intent.extras!!.getSerializable("Game") as Game
        setContentView(R.layout.settings_menu)
        findViewById<ImageButton>(R.id.BackButton).setOnClickListener {
            val intent = Intent(this, GameScreen::class.java)
            intent.putExtra("Game", game)
            startActivity(intent)
            finish()
        }
        findViewById<ImageButton>(R.id.ResetGameButton).setOnClickListener {
            intent.putExtra("Game", Game(game.getSettings()))
            val intent = Intent(this, GameScreen::class.java)
            startActivity(intent)
            finish()
        }
        findViewById<ImageButton>(R.id.SettingsButton).setOnClickListener {
            val intent = Intent(this, GameConfigMenu::class.java)
            intent.putExtra("Game", game)
            startActivity(intent)
            finish()
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        val intent = Intent(this, GameScreen::class.java)
        intent.putExtra("Game", game)
        startActivity(intent)
        finish()
    }
}
