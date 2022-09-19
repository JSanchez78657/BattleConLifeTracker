package com.example.battleconlifetracker.layouts

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.widget.EditText
import android.widget.ImageButton
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.battleconlifetracker.R
import com.example.battleconlifetracker.model.game.GameFlags

class SettingsMenu : AppCompatActivity() {

   private var bossPlayerCount = -1

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
        findViewById<ImageButton>(R.id.DuelButton).setOnClickListener {
            setResult(GameFlags.DUEL.intVal)
            finish()
        }
        findViewById<ImageButton>(R.id.TeamsButton).setOnClickListener {
            setResult(GameFlags.TEAMS.intVal)
            finish()
        }
        findViewById<ImageButton>(R.id.SuperButton).setOnClickListener {
            setResult(GameFlags.SUPER.intVal)
            finish()
        }
        findViewById<ImageButton>(R.id.UltraButton).setOnClickListener {
            setResult(GameFlags.ULTRA.intVal)
            finish()
        }
        findViewById<ImageButton>(R.id.BossButton).setOnClickListener {
            while(bossPlayerCount !in setOf(-1, 2, 3, 4))
                getPlayerCount()
            if(bossPlayerCount != -1) {
                intent.putExtra("BossPlayerCount", bossPlayerCount)
                setResult(GameFlags.BOSS.intVal)
                finish()
            }
        }
    }

    override fun onBackPressed() {
        setResult(GameFlags.RESUME.intVal)
        finish()
    }

    private fun getPlayerCount() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Player Count")
        val input = EditText(this)
        input.hint = "Number of players facing the boss (2-4)"
        input.inputType = InputType.TYPE_CLASS_NUMBER
        builder.setView(input)
        builder.setPositiveButton("OK", DialogInterface.OnClickListener { dialog, which ->
            // Here you get get input text from the Edittext
            bossPlayerCount = input.text.toString().toInt()
        })
        builder.setNegativeButton("Cancel", DialogInterface.OnClickListener { dialog, which -> dialog.cancel() })
        builder.show()
    }
}
