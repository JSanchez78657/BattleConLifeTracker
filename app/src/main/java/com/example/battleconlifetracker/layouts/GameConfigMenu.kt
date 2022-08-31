package com.example.battleconlifetracker.layouts

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.battleconlifetracker.R
import com.example.battleconlifetracker.model.game.Game
import com.example.battleconlifetracker.model.GameSettings

class GameConfigMenu : AppCompatActivity() {

    private var game = Game(GameSettings(20, 2, 45, 2))

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if(intent?.extras?.getSerializable("Game") != null)
            game = intent.extras!!.getSerializable("Game") as Game
        setContentView(R.layout.game_config)
//        val spinner = findViewById<Spinner>(R.id.PlayerNumberSpinner)
//        val arr = mutableListOf<String?>()
//        for(i in 0 until 5) arr.add(i, (i + 1).toString())
//        val arrAdapter = ArrayAdapter<Any?>(this, R.layout.spinner, arr.toTypedArray())
//        arrAdapter.setDropDownViewResource(R.layout.spinner)
//        spinner.adapter = arrAdapter
//        spinner.setSelection(game.getPlayerCount() - 1)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        val intent = Intent(this, GameScreen::class.java)
        intent.putExtra("Game", intent.getSerializableExtra("Game"))
        startActivity(intent)
        finish()
    }
}