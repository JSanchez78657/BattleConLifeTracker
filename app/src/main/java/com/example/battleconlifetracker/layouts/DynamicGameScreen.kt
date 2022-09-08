package com.example.battleconlifetracker.layouts

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import com.example.battleconlifetracker.R

class DynamicGameScreen : AppCompatActivity() {

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
}