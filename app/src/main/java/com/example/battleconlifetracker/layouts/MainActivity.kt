package com.example.battleconlifetracker.layouts

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.battleconlifetracker.model.game.Game
import com.example.battleconlifetracker.model.GameSettings

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val intent = Intent(this, GameScreen::class.java)
        val extras = Bundle()
        extras.putSerializable("Game", Game(GameSettings(20, 2, 45, 2)))
        intent.putExtras(extras)
        startActivity(intent)
        finish()
    }
}