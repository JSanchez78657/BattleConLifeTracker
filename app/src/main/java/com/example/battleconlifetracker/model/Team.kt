package com.example.battleconlifetracker.model

import com.example.battleconlifetracker.model.player.Player

class Team (vararg val playerArray: Player) {

    private val maxForce = 20
    private var currentForce = 0
    private var forcePerBeat = 0

    init {
        playerArray.forEach { player -> forcePerBeat += player.forcePerBeat }
        updateForcePerBeat()
    }

    fun updateForcePerBeat() {
        var playerForce = 0
        playerArray.forEach { player -> playerForce += player.forcePerBeat }
        forcePerBeat = playerForce
    }

    fun endOfBeatForce(): Int {
        currentForce += forcePerBeat
        if (currentForce > maxForce) currentForce = maxForce
        return forcePerBeat
    }
}