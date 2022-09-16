package com.example.battleconlifetracker.layouts

import android.app.Activity
import android.os.Bundle
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import com.example.battleconlifetracker.R
import com.example.battleconlifetracker.model.game.GameFlags

class SettingsMenu : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.settings_menu)
        findViewById<ImageButton>(R.id.BackButton).setOnClickListener {
            setResult(GameFlags.RESUME.intVal)
            finish()
        }
        findViewById<ImageButton>(R.id.ResetGameButton).setOnClickListener {
            setResult(GameFlags.RESET.intVal)
            finish()
        }
    }

    override fun onBackPressed() {
        setResult(GameFlags.RESUME.intVal)
        finish()
    }
}
