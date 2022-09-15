package com.example.battleconlifetracker.layouts

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatImageButton
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.battleconlifetracker.R
import com.example.battleconlifetracker.helpers.EasyConstraintSet
import com.example.battleconlifetracker.model.game.TeamGame
import com.google.android.material.textview.MaterialTextView

class DynamicGameScreen : AppCompatActivity() {

    //ViewId hashes to the tuple defining the team and player index within that team.
    private val viewsToPlayerIndex: HashMap<Int, Pair<Int, Int>> = hashMapOf()
    val game = TeamGame()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.blank_constraint)
        val constraintLayout = findViewById<ConstraintLayout>(R.id.GameConstraint)
        constraintLayout.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        buildTeamGame(constraintLayout, 2, 2)
        setContentView(constraintLayout)
        game.initGame()
        refreshUI()
    }

    private fun example() {
        val constraintLayout = findViewById<ConstraintLayout>(R.id.GameConstraint)
        val health = layoutInflater.inflate(R.layout.health_block_horizontal, constraintLayout, false)
        val force = layoutInflater.inflate(R.layout.force_block_horizontal, constraintLayout, false)
        health.id = View.generateViewId()
        force.id = View.generateViewId()
        constraintLayout.addView(health)
        constraintLayout.addView(force)

        EasyConstraintSet.joinConstraintViewsLeftToRight(constraintLayout, listOf(health, force))
        setContentView(constraintLayout)
    }

    private fun buildTeamGame(parent: ConstraintLayout, teamAPlayerCount: Int, teamBPlayerCount: Int): View {
        val teamA = buildTeam(parent, teamAPlayerCount)
        val teamB = buildTeam(parent, teamBPlayerCount)
        val beatZone = layoutInflater.inflate(R.layout.end_of_beat_block_vertical, parent, false)
        teamA.rotation = 180f
        beatZone.findViewById<ImageButton>(R.id.EndBeatButton).setOnClickListener {
            game.endBeat()
            refreshUI()
        }
        parent.addView(teamA)
        parent.addView(beatZone)
        parent.addView(teamB)
        EasyConstraintSet.joinConstraintViewsLeftToRight(parent, listOf(teamA,beatZone, teamB))
        return parent
    }

    private fun buildTeam(parent: ConstraintLayout, teamSize: Int): ConstraintLayout {
        val teamFrame = layoutInflater.inflate(R.layout.blank_constraint, parent, false) as ConstraintLayout
        val teamId = View.generateViewId()
        val uiBlocks: MutableList<View> = mutableListOf()
        var idHold: Int
        teamFrame.id = teamId
        game.addTeam(teamId, teamSize)
        for(playerId in 0 until teamSize) {
            idHold = View.generateViewId()
            uiBlocks.add(playerId, layoutInflater.inflate(R.layout.health_block_horizontal, teamFrame, false))
            uiBlocks[playerId].id = idHold
            viewsToPlayerIndex[idHold] = Pair(teamId, playerId)
            setHealthBlockListeners(uiBlocks[playerId])
            teamFrame.addView(uiBlocks[playerId])
        }
        idHold = View.generateViewId()
        uiBlocks.add(uiBlocks.size, layoutInflater.inflate(R.layout.force_block_horizontal, teamFrame, false))
        uiBlocks.last().id = idHold
        viewsToPlayerIndex[idHold] = Pair(teamId, -1)
        setForceBlockListeners(uiBlocks.last())
        teamFrame.addView(uiBlocks.last())
        EasyConstraintSet.joinConstraintViewsBottomToTop(teamFrame, uiBlocks.toList())
        return teamFrame
    }

    private fun refreshUI() {
        updateText()
        updateButtons()
    }

    private fun getViewFromConstraintLayout(constraintLayoutKey: Int, viewKey: Int): View {
        val hold = findViewById<ConstraintLayout>(constraintLayoutKey)
        return hold.findViewById(viewKey)
    }

    private fun updateText() {
        val beatBlock = findViewById<ConstraintLayout>(R.id.EndOfBeatBlock)
        viewsToPlayerIndex.forEach { (key, pair) ->
            if(pair.second != -1) {
                (getViewFromConstraintLayout(key, R.id.LifeTotal) as TextView).text = game.teamMap[pair.first]?.playerList?.get(pair.second)?.currentHealth.toString()
            }
            else
                (getViewFromConstraintLayout(key, R.id.ForceTotal) as TextView).text = game.teamMap[pair.first]?.currentForce.toString()
        }
        beatBlock.findViewById<MaterialTextView>(R.id.RemainingForceLeft).text = game.getRemainingForce().toString()
        beatBlock.findViewById<MaterialTextView>(R.id.RemainingForceRight).text = game.getRemainingForce().toString()
    }

    private fun updateButtons() {
        val availableActions = game.getAvailableActions()
        viewsToPlayerIndex.forEach{ (view, pair) ->
            val teamId = pair.first
            val playerId = pair.second
            if(playerId == -1) return@forEach
            getViewFromConstraintLayout(view, R.id.FinisherButton).visibility = getVisibility(availableActions[teamId]?.get(playerId)?.contains("FINISHER")!!)
            getViewFromConstraintLayout(view, R.id.PowerAnteButton).visibility = getVisibility(availableActions[teamId]?.get(playerId)?.contains("POWER")!!)
            getViewFromConstraintLayout(view, R.id.GuardAnteButton).visibility = getVisibility(availableActions[teamId]?.get(playerId)?.contains("GUARD")!!)
            getViewFromConstraintLayout(view, R.id.PrioAnteButton).visibility = getVisibility(availableActions[teamId]?.get(playerId)?.contains("PRIORITY")!!)
        }
    }

    private fun getVisibility(condition: Boolean): Int {
        return if(condition) View.VISIBLE else View.INVISIBLE
    }

    private fun setHealthBlockListeners(key: View) {
        val team = viewsToPlayerIndex[key.id]?.first ?: return
        val player = viewsToPlayerIndex[key.id]?.second ?: return
        key.findViewById<AppCompatImageButton>(R.id.GainLife).setOnClickListener {
            game.changeHealth(team, player, 1)
            refreshUI()
        }
        key.findViewById<AppCompatImageButton>(R.id.LoseLife).setOnClickListener {
            game.changeHealth(team, player, -1)
            refreshUI()
        }
        key.findViewById<AppCompatImageButton>(R.id.FinisherButton).setOnClickListener {
            game.useFinisher(team, player)
            refreshUI()
        }
        key.findViewById<AppCompatImageButton>(R.id.PowerAnteButton).setOnClickListener {
            game.useOverload("POWER", team, player)
            refreshUI()
        }
        key.findViewById<AppCompatImageButton>(R.id.GuardAnteButton).setOnClickListener {
            game.useOverload("GUARD", team, player)
            refreshUI()
        }
        key.findViewById<AppCompatImageButton>(R.id.PrioAnteButton).setOnClickListener {
            game.useOverload("PRIORITY", team, player)
            refreshUI()
        }
    }

    private fun setForceBlockListeners(key: View) {
        val team = viewsToPlayerIndex[key.id]?.first ?: return
        key.findViewById<AppCompatImageButton>(R.id.GainForce).setOnClickListener {
            game.changeForce(team, 1).toString()
            refreshUI()
        }
        key.findViewById<AppCompatImageButton>(R.id.LoseForce).setOnClickListener {
            game.changeForce(team, -1).toString()
            refreshUI()
        }
    }


}