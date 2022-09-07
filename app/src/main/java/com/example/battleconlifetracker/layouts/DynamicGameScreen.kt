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
        var constraintLayout = findViewById<ConstraintLayout>(R.id.TeamConstraint)
        var constraintSet = ConstraintSet()
        var topButton = ImageButton(constraintLayout.context)
        var bottomButton = ImageButton(constraintLayout.context)

        topButton.id = View.generateViewId()
        topButton.scaleType = ImageView.ScaleType.FIT_CENTER
        topButton.layoutParams = ViewGroup.LayoutParams(0,0)
        topButton.setImageResource(R.drawable.freedom)

        bottomButton.id = View.generateViewId()
        bottomButton.scaleType = ImageView.ScaleType.FIT_CENTER
        bottomButton.layoutParams = ViewGroup.LayoutParams(0,0)
        bottomButton.setImageResource(R.drawable.dragon)

        constraintLayout.addView(topButton)
        constraintLayout.addView(bottomButton)

        constraintSet.clone(constraintLayout)
        constraintSet.connect(topButton.id, ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP)
        constraintSet.connect(topButton.id, ConstraintSet.BOTTOM, bottomButton.id, ConstraintSet.TOP)
        constraintSet.connect(topButton.id, ConstraintSet.START, ConstraintSet.PARENT_ID, ConstraintSet.START)
        constraintSet.connect(topButton.id, ConstraintSet.END, ConstraintSet.PARENT_ID, ConstraintSet.END)

        constraintSet.connect(bottomButton.id, ConstraintSet.TOP, topButton.id, ConstraintSet.BOTTOM)
        constraintSet.connect(bottomButton.id, ConstraintSet.BOTTOM, ConstraintSet.PARENT_ID, ConstraintSet.BOTTOM)
        constraintSet.connect(bottomButton.id, ConstraintSet.START, ConstraintSet.PARENT_ID, ConstraintSet.START)
        constraintSet.connect(bottomButton.id, ConstraintSet.END, ConstraintSet.PARENT_ID, ConstraintSet.END)
        constraintSet.applyTo(constraintLayout)

        setContentView(constraintLayout)
    }
}