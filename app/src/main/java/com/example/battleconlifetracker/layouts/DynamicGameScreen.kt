package com.example.battleconlifetracker.layouts

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import com.example.battleconlifetracker.R
import com.example.battleconlifetracker.helpers.EasyConstraintSet
import com.example.battleconlifetracker.model.Team

class DynamicGameScreen : AppCompatActivity() {

    //ViewId hashes to the tuple defining the team and player index within that team.
    val viewsToPlayerIndex: HashMap<Int, Pair<Int, Int>> = hashMapOf()
    val teamMap: HashMap<Int, Team> = hashMapOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.blank_constraint)
        val constraintLayout = findViewById<ConstraintLayout>(R.id.GameConstraint)
        constraintLayout.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        buildTeamGame(constraintLayout, 2, 3)
        setContentView(constraintLayout)
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
        val beatZone = layoutInflater.inflate(R.layout.end_of_beat_block, parent, false)
        teamA.rotation = 180f
        beatZone.id = View.generateViewId()
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
            teamFrame.addView(trackers[i])
        }
        idHold = View.generateViewId()
        trackers.add(trackers.size, layoutInflater.inflate(R.layout.force_block_horizontal, teamFrame, false))
        trackers.last().id = idHold
        viewsToPlayerIndex[idHold] = Pair(teamId, -1)
        teamFrame.addView(trackers.last())
        EasyConstraintSet.joinConstraintViewsBottomToTop(teamFrame, trackers.toList())
        return teamFrame
    }
}