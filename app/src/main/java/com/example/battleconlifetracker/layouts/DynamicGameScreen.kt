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
        setContentView(R.layout.blank_constraint)
        var constraintLayout = findViewById<ConstraintLayout>(R.id.GameConstraint)
        val playerA = layoutInflater.inflate(R.layout.blank_constraint, constraintLayout) as ConstraintLayout
        val playerB = layoutInflater.inflate(R.layout.blank_constraint, constraintLayout) as ConstraintLayout

        var health = layoutInflater.inflate(R.layout.health_block_horizontal, playerA, false)
        var force = layoutInflater.inflate(R.layout.force_block_horizontal, playerA, false)
        var endOfBeat = layoutInflater.inflate(R.layout.end_of_beat_block, constraintLayout, false)
        var healthB = layoutInflater.inflate(R.layout.health_block_horizontal, playerB, false)
        var forceB = layoutInflater.inflate(R.layout.force_block_horizontal, playerB, false)

        health.id = View.generateViewId()
        healthB.id = View.generateViewId()
        force.id = View.generateViewId()
        forceB.id = View.generateViewId()
        endOfBeat.id = View.generateViewId()

        playerA.id = View.generateViewId()
        playerB.id = View.generateViewId()

        playerA.addView(health)
        playerA.addView(force)
        playerB.addView(healthB)
        playerB.addView(forceB)

        joinConstraintViewsBottomToTop(playerB, listOf(healthB, forceB))
        joinConstraintViewsBottomToTop(playerA, listOf(health, force))

//        constraintLayout.addView(playerA)
//        constraintLayout.addView(playerB)
//        joinConstraintViewsLeftToRight(constraintLayout, listOf(playerA, playerB, endOfBeat))
//        constraintLayout.addView(health)
//        constraintLayout.addView(force)
//        constraintLayout.addView(endOfBeat)

//        joinConstraintViewsBottomToTop(constraintLayout, listOf(health, force, endOfBeat))
//        constraints.getConstraint(endOfBeat.id).transform.transformPivotX = 90f

//        constraints.clone(constraintLayout)
//
//        constraints.connect(health.id, ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP)
//        constraints.connect(health.id, ConstraintSet.BOTTOM, ConstraintSet.PARENT_ID, ConstraintSet.BOTTOM)
//        constraints.connect(health.id, ConstraintSet.START, ConstraintSet.PARENT_ID, ConstraintSet.START)
//        constraints.connect(health.id, ConstraintSet.END, force.id, ConstraintSet.START)
//
//        constraints.connect(force.id, ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP)
//        constraints.connect(force.id, ConstraintSet.BOTTOM, ConstraintSet.PARENT_ID, ConstraintSet.BOTTOM)
//        constraints.connect(force.id, ConstraintSet.START, health.id, ConstraintSet.END)
//        constraints.connect(force.id, ConstraintSet.END, ConstraintSet.PARENT_ID, ConstraintSet.END)

//        constraints.applyTo(constraintLayout)
        setContentView(playerB)
//        setContentView(buildTeamGame(findViewById(R.id.GameConstraint), 2, 2))
    }

    private fun rotateLayoutClockwise(constraintLayout: ConstraintLayout) {
        //Top, End, Bottom, Start
        //Clockwise rotation moves each to the right one.
//        val constraints = ConstraintSet()
//        constraints.clone(constraintLayout)
//        constraints.getConstraint(constraintLayout.id).transform.rotation = 90.0f
//        constraints.connect(constraintLayout.id, ConstraintSet.TOP)
    }

    private fun buildTeamGame(parent: ConstraintLayout, teamAPlayerCount: Int, teamBPlayerCount: Int): View {
        var teamA = buildTeam(parent, teamAPlayerCount)
        var teamB = buildTeam(parent, teamBPlayerCount)
        var beatZone = layoutInflater.inflate(R.layout.end_of_beat_block, parent, false)
        teamA.rotation = 90.0f
        teamB.rotation = -90.0f
        beatZone.rotation = 90.0f
        val views = listOf<View>(teamA, beatZone, teamB)
        joinConstraintViewsLeftToRight(parent, views)
        return parent
    }

    private fun buildTeam(parent: ConstraintLayout, teamSize: Int): ConstraintLayout {
        var teamFrame = ConstraintLayout(parent.context)
        val teamId = View.generateViewId()
        var trackers: MutableList<View> = mutableListOf()
        var idHold: Int
        teamMap[teamId] = Team(teamSize)
        for(i in 0 until teamSize) {
            idHold = View.generateViewId()
            trackers.add(i, layoutInflater.inflate(R.layout.health_block_vertical, teamFrame, false))
            trackers[i].id = idHold
            viewsToPlayerIndex[idHold] = Pair(teamId, i)
            teamFrame.addView(trackers[i])
        }
        idHold = View.generateViewId()
        trackers.add(trackers.size, layoutInflater.inflate(R.layout.force_block_vertical, teamFrame, false))
        trackers.last().id = idHold
        viewsToPlayerIndex[idHold] = Pair(teamId, -1)
        joinConstraintViewsBottomToTop(teamFrame, trackers.toList())
        return teamFrame
    }

    private fun joinConstraintViewsLeftToRight(parent: ConstraintLayout, views: List<View>) {
        val constraints = ConstraintSet()
        constraints.clone(parent)
        for(i in views.indices) {
            constraints.connect(views[i].id, ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP)
            constraints.connect(views[i].id, ConstraintSet.BOTTOM, ConstraintSet.PARENT_ID, ConstraintSet.BOTTOM)
            //Middle
            if(i - 1 in views.indices && i + 1 in views.indices) {
                constraints.connect(views[i].id, ConstraintSet.START, views[i - 1].id, ConstraintSet.END)
                constraints.connect(views[i].id, ConstraintSet.END, views[i + 1].id, ConstraintSet.START)
            }
            //First
            else if(i + 1 in views.indices) {
                constraints.connect(views[i].id, ConstraintSet.START, ConstraintSet.PARENT_ID, ConstraintSet.START)
                constraints.connect(views[i].id, ConstraintSet.END, views[i + 1].id, ConstraintSet.START)
            }
            //Last
            else if(i - 1 in views.indices) {
                constraints.connect(views[i].id, ConstraintSet.START, views[i - 1].id, ConstraintSet.END)
                constraints.connect(views[i].id, ConstraintSet.END, ConstraintSet.PARENT_ID, ConstraintSet.END)
            }
        }
        constraints.applyTo(parent)
    }

    private fun joinConstraintViewsBottomToTop(parent: ConstraintLayout, views: List<View>) {
        val constraints = ConstraintSet()
        constraints.clone(parent)
        for(i in views.indices) {
            constraints.connect(views[i].id, ConstraintSet.START, ConstraintSet.PARENT_ID, ConstraintSet.START)
            constraints.connect(views[i].id, ConstraintSet.END, ConstraintSet.PARENT_ID, ConstraintSet.END)
            //Middle
            if(i - 1 in views.indices && i + 1 in views.indices) {
                constraints.connect(views[i].id, ConstraintSet.BOTTOM, views[i - 1].id, ConstraintSet.TOP)
                constraints.connect(views[i].id, ConstraintSet.TOP, views[i + 1].id, ConstraintSet.BOTTOM)
            }
            //First
            else if(i + 1 in views.indices) {
                constraints.connect(views[i].id, ConstraintSet.BOTTOM, ConstraintSet.PARENT_ID, ConstraintSet.BOTTOM)
                constraints.connect(views[i].id, ConstraintSet.TOP, views[i + 1].id, ConstraintSet.BOTTOM)
            }
            //Last
            else if(i - 1 in views.indices) {
                constraints.connect(views[i].id, ConstraintSet.BOTTOM, views[i - 1].id, ConstraintSet.TOP)
                constraints.connect(views[i].id, ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP)
            }
        }
        constraints.applyTo(parent)
    }
}