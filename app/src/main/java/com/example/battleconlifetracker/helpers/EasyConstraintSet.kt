package com.example.battleconlifetracker.helpers

import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet

class EasyConstraintSet {

    companion object {
        fun joinConstraintViewsLeftToRight(parent: ConstraintLayout, views: List<View>) {
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

        fun joinConstraintViewsBottomToTop(parent: ConstraintLayout, views: List<View>) {
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

}