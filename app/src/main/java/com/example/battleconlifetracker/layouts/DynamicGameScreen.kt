package com.example.battleconlifetracker.layouts

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatImageButton
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.children
import com.example.battleconlifetracker.R
import com.example.battleconlifetracker.helpers.EasyConstraintSet
import com.example.battleconlifetracker.model.Team
import com.example.battleconlifetracker.model.game.TeamGame
import com.google.android.material.textview.MaterialTextView

class DynamicGameScreen : AppCompatActivity() {

    //ViewId hashes to the tuple defining the team and player index within that team.
    val viewsToPlayerIndex: HashMap<Int, Pair<Int, Int>> = hashMapOf()
    val teamMap: HashMap<Int, Team> = hashMapOf()
    val game = TeamGame(2, 2)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.blank_constraint)
        val constraintLayout = findViewById<ConstraintLayout>(R.id.GameConstraint)
        constraintLayout.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        buildTeamGame(constraintLayout, game.teamArray[0].playerArray.size, game.teamArray[1].playerArray.size)
        setContentView(constraintLayout)
        updateText()
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
        val trackers: MutableList<View> = mutableListOf()
        var idHold: Int
        teamFrame.id = teamId
        teamMap[teamId] = Team(teamSize)
        for(i in 0 until teamSize) {
            idHold = View.generateViewId()
            trackers.add(i, layoutInflater.inflate(R.layout.health_block_horizontal, teamFrame, false))
            trackers[i].id = idHold
            viewsToPlayerIndex[idHold] = Pair(teamId, i)
            setHealthBlockListeners(trackers[i])
            teamFrame.addView(trackers[i])
        }
        idHold = View.generateViewId()
        trackers.add(trackers.size, layoutInflater.inflate(R.layout.force_block_horizontal, teamFrame, false))
        trackers.last().id = idHold
        viewsToPlayerIndex[idHold] = Pair(teamId, -1)
        setForceBlockListeners(trackers.last())
        teamFrame.addView(trackers.last())
        EasyConstraintSet.joinConstraintViewsBottomToTop(teamFrame, trackers.toList())
        return teamFrame
    }

    private fun updateText() {
        val beatBlock = findViewById<ConstraintLayout>(R.id.EndOfBeatBlock)
        viewsToPlayerIndex.forEach { (key, pair) ->
            if(pair.second != -1)
                getTextView(key).text = teamMap[pair.first]?.playerArray?.get(pair.second)?.currentHealth.toString()
            else
                getTextView(key).text = teamMap[pair.first]?.currentForce.toString()
        }
        beatBlock.findViewById<MaterialTextView>(R.id.RemainingForceLeft).text = game.getRemainingForce().toString()
        beatBlock.findViewById<MaterialTextView>(R.id.RemainingForceRight).text = game.getRemainingForce().toString()
    }

    private fun updateButtons() {

    }

    private fun getTextView(key: Int): TextView {
        return findViewById<ConstraintLayout>(key).children.find { view -> view is MaterialTextView } as TextView
    }

    private fun setHealthBlockListeners(key: View) {
        val team = viewsToPlayerIndex[key.id]?.first
        val player = viewsToPlayerIndex[key.id]?.second
        val label = key.findViewById<MaterialTextView>(R.id.LifeTotal)
        if(team == null || player == null) return
        key.findViewById<AppCompatImageButton>(R.id.GainLife).setOnClickListener {
            label.text = game.changeHealth(team, player, 1).toString()
        }
        key.findViewById<AppCompatImageButton>(R.id.LoseLife).setOnClickListener {
            label.text = game.changeHealth(team, player, -1).toString()
        }
        key.findViewById<AppCompatImageButton>(R.id.FinisherButton).setOnClickListener {
            game.useFinisher(team, player)
        }
    }

    private fun setForceBlockListeners(key: View) {
        val team = viewsToPlayerIndex[key.id]?.first
        val label = key.findViewById<MaterialTextView>(R.id.ForceTotal)
        if(team == null) return
        key.findViewById<AppCompatImageButton>(R.id.GainForce).setOnClickListener {
            label.text = game.changeForce(team, 1).toString()
        }
        key.findViewById<AppCompatImageButton>(R.id.LoseForce).setOnClickListener {
            label.text = game.changeForce(team, -1).toString()
        }
    }
}