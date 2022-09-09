package com.example.battleconlifetracker.layouts

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import com.example.battleconlifetracker.R
import com.example.battleconlifetracker.model.Team

class DynamicGameScreen : AppCompatActivity() {

    //ViewId hashes to the tuple defining the team and player index within that team.
    val viewsToPlayerIndex: HashMap<Int, Pair<Int, Int>> = hashMapOf()
    val teamMap: HashMap<Int, Team> = hashMapOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.team_game)
        var constraintLayout = findViewById<ConstraintLayout>(R.id.GameConstraint)

        var health = layoutInflater.inflate(R.layout.health_block, constraintLayout, false)
        var force = layoutInflater.inflate(R.layout.force_block, constraintLayout, false)

        var constraints = ConstraintSet()

        health.id = View.generateViewId()
        force.id = View.generateViewId()

        constraintLayout.addView(health)
        constraintLayout.addView(force)

        constraints.clone(constraintLayout)

        constraints.connect(health.id, ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP)
        constraints.connect(health.id, ConstraintSet.BOTTOM, ConstraintSet.PARENT_ID, ConstraintSet.BOTTOM)
        constraints.connect(health.id, ConstraintSet.START, ConstraintSet.PARENT_ID, ConstraintSet.START)
        constraints.connect(health.id, ConstraintSet.END, force.id, ConstraintSet.START)

        constraints.connect(force.id, ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP)
        constraints.connect(force.id, ConstraintSet.BOTTOM, ConstraintSet.PARENT_ID, ConstraintSet.BOTTOM)
        constraints.connect(force.id, ConstraintSet.START, health.id, ConstraintSet.END)
        constraints.connect(force.id, ConstraintSet.END, ConstraintSet.PARENT_ID, ConstraintSet.END)

        constraints.applyTo(constraintLayout)
        setContentView(constraintLayout)
    }

    private fun buildTeamGame(parent: ConstraintLayout, teamAPlayerCount: Int, teamBPlayerCount: Int) {
        var teamA = buildTeam(parent, teamAPlayerCount)
        var teamB = buildTeam(parent, teamBPlayerCount)
        teamA.rotation = 90.0f
        teamB.rotation = -90.0f
    }

    private fun buildTeam(parent: ConstraintLayout, teamSize: Int): ConstraintLayout {
        var teamFrame = ConstraintLayout(parent.context)
        var teamForce = layoutInflater.inflate(R.layout.force_block, teamFrame, false)
        val teamId = View.generateViewId()
        var healthTrackers: MutableList<View> = mutableListOf()
        var idHold: Int
        teamFrame.addView(teamForce)
        teamForce.id = teamId
        teamMap[teamId] = Team(teamSize)
        for(i in 0 until teamSize) {
            idHold = View.generateViewId()
            healthTrackers.add(i, layoutInflater.inflate(R.layout.health_block, teamFrame, false))
            healthTrackers[i].id = idHold
            viewsToPlayerIndex[idHold] = Pair(teamId, i)
            teamFrame.addView(healthTrackers[i])
        }
        return teamFrame
    }
}