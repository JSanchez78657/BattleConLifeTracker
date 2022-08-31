package com.example.battleconlifetracker.model

import com.example.battleconlifetracker.model.player.Player

class Team (vararg players: Player){

    private val playerList = players.toList()
    private val maxForce = 20
    private var currentForce = 0
    private var forcePerBeat = 0

    init {
        playerList.forEach{ player -> forcePerBeat += player.forcePerBeat }
    }

    fun endOfBeatForce(): Int {
        currentForce += forcePerBeat
        if (currentForce > maxForce) currentForce = maxForce
        return forcePerBeat
    }
}