package com.example.battleconlifetracker.layouts

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.battleconlifetracker.model.game.Game
import com.example.battleconlifetracker.R
import com.example.battleconlifetracker.model.GameSettings

class GameScreen : AppCompatActivity() {

    private var game = Game(GameSettings(20, 2, 45, 2))

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if(intent?.extras?.getSerializable("Game") != null)
            game = intent.extras!!.getSerializable("Game") as Game
        setContentView(R.layout.activity_main)
        val p1Life = findViewById<TextView>(R.id.P1LifeTotal)
        val p1Force = findViewById<TextView>(R.id.P1ForceTotal)
        val p2Life = findViewById<TextView>(R.id.P2LifeTotal)
        val p2Force = findViewById<TextView>(R.id.P2ForceTotal)
        val p1RemainingForce = findViewById<TextView>(R.id.P1RemainingForce)
        val p2RemainingForce = findViewById<TextView>(R.id.P2RemainingForce)

        p1Life.text = game.getPlayer(0)?.currentLife.toString()
        p1Force.text = game.getPlayer(0)?.currentForce.toString()
        p1RemainingForce.text = game.getRemainingForce().toString()
        p2Life.text = game.getPlayer(1)?.currentLife.toString()
        p2Force.text = game.getPlayer(1)?.currentForce.toString()
        p2RemainingForce.text = game.getRemainingForce().toString()


        p1Life.bringToFront()
        p1Force.bringToFront()
        p1RemainingForce.bringToFront()
        p2Life.bringToFront()
        p2Force.bringToFront()
        p2RemainingForce.bringToFront()

        //P1 Gain/Lose life
        findViewById<ImageButton>(R.id.P1GainLife).setOnClickListener {
            p1Life.text = game.changeHealth(0, 1).toString()
            checkButtons()
        }
        findViewById<ImageButton>(R.id.P1LoseLife).setOnClickListener {
            p1Life.text = game.changeHealth(0, -1).toString()
            checkButtons()
        }
        //P1 Gain/Lose force
        findViewById<ImageButton>(R.id.P1GainForce).setOnClickListener {
            p1Force.text = game.changeForce(0, 1).toString()
            checkButtons()
        }
        findViewById<ImageButton>(R.id.P1LoseForce).setOnClickListener {
            p1Force.text = game.changeForce(0, -1).toString()
            checkButtons()
        }
        //P1 Finisher
        findViewById<ImageButton>(R.id.P1FinisherButton).setOnClickListener {
            p1Force.text = game.useFinisher(0).toString()
            checkButtons()
        }
        //P1 Overload Actions
        findViewById<ImageButton>(R.id.P1PowerAnteButton).setOnClickListener {
            if(game.overloadAvailable(0, "POWER")) {
                p1Force.text = game.useOverload(0, "POWER").toString()
                checkButtons()
            }
        }
        findViewById<ImageButton>(R.id.P1GuardAnteButton).setOnClickListener {
            if(game.overloadAvailable(0, "GUARD")) {
                p1Force.text = game.useOverload(0, "GUARD").toString()
                checkButtons()
            }
        }
        findViewById<ImageButton>(R.id.P1PrioAnteButton).setOnClickListener {
            if(game.overloadAvailable(0, "PRIORITY")) {
                p1Force.text = game.useOverload(0, "PRIORITY").toString()
                checkButtons()
            }
        }
        //P2 Gain/Lose life
        findViewById<ImageButton>(R.id.P2GainLife).setOnClickListener {
            p2Life.text = game.changeHealth(1, 1).toString()
            checkButtons()
        }
        findViewById<ImageButton>(R.id.P2LoseLife).setOnClickListener {
            p2Life.text = game.changeHealth(1, -1).toString()
            checkButtons()
        }

        //P2 Gain/Lose force
        findViewById<ImageButton>(R.id.P2GainForce).setOnClickListener {
            p2Force.text = game.changeForce(1, 1).toString()
            checkButtons()
        }
        findViewById<ImageButton>(R.id.P2LoseForce).setOnClickListener {
            p2Force.text = game.changeForce(1, -1).toString()
            checkButtons()
        }
        //P2 Finisher
        findViewById<ImageButton>(R.id.P2FinisherButton).setOnClickListener {
            p2Force.text = game.useFinisher(1).toString()
            checkButtons()
        }
        //P2 Overload actions
        findViewById<ImageButton>(R.id.P2PowerAnteButton).setOnClickListener {
            if(game.overloadAvailable(1, "POWER")) {
                p2Force.text = game.useOverload(1, "POWER").toString()
                checkButtons()
            }
        }
        findViewById<ImageButton>(R.id.P2GuardAnteButton).setOnClickListener {
            if(game.overloadAvailable(1, "GUARD")) {
                p2Force.text = game.useOverload(1, "GUARD").toString()
                checkButtons()
            }
        }
        findViewById<ImageButton>(R.id.P2PrioAnteButton).setOnClickListener {
            if(game.overloadAvailable(1, "PRIORITY")) {
                p2Force.text = game.useOverload(1, "PRIORITY").toString()
                checkButtons()
            }
        }
        //End Beat
        findViewById<ImageButton>(R.id.EndBeatButton).setOnClickListener {
            game.endBeat()
            p1Force.text = game.getPlayer(0)?.currentForce.toString()
            p2Force.text = game.getPlayer(1)?.currentForce.toString()
            p1RemainingForce.text = game.getRemainingForce().toString()
            p2RemainingForce.text = game.getRemainingForce().toString()
            checkButtons()
        }
        //Menu button
        findViewById<ImageButton>(R.id.MenuButton).setOnClickListener {
            val intent = Intent(this, SettingsMenu::class.java)
            intent.putExtra("Game", game)
            startActivity(intent)
            finish()
        }
        checkButtons()
    }

    private fun checkButtons() {
        val availableActions: List<List<String>> = game.checkAvailableActions()
        //Player 1 actions
        conditionalVisibility(availableActions[0].contains("FINISHER"), R.id.P1FinisherButton)
        conditionalVisibility(availableActions[0].contains("POWER"), R.id.P1PowerAnteButton)
        conditionalVisibility(availableActions[0].contains("GUARD"), R.id.P1GuardAnteButton)
        conditionalVisibility(availableActions[0].contains("PRIORITY"), R.id.P1PrioAnteButton)
        //Player 2 actions
        conditionalVisibility(availableActions[1].contains("FINISHER"), R.id.P2FinisherButton)
        conditionalVisibility(availableActions[1].contains("POWER"), R.id.P2PowerAnteButton)
        conditionalVisibility(availableActions[1].contains("GUARD"), R.id.P2GuardAnteButton)
        conditionalVisibility(availableActions[1].contains("PRIORITY"), R.id.P2PrioAnteButton)
    }

    private fun setImgBtnVisibility(id: Int, visibility: Int) {
        findViewById<ImageButton>(id).visibility = visibility
    }

    private fun conditionalVisibility(test: Boolean, id: Int) {
        if(test)
            setImgBtnVisibility(id, View.VISIBLE)
        else
            setImgBtnVisibility(id, View.INVISIBLE)
    }
}