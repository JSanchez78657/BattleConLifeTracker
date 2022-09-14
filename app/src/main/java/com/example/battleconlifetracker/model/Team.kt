package com.example.battleconlifetracker.model

import android.view.View
import com.example.battleconlifetracker.model.player.Player

class Team (vararg players: Player) {

    private val maxForce = if(players.size > 1) 20 else 10
    private var forcePerBeat = 0
    var currentForce = 0
    var playerArray: Array<Player> = arrayOf()

    init {
        for(i in players.indices) {
            playerArray += players[i]
            forcePerBeat += players[i].forcePerBeat
            currentForce += players[i].currentForce
        }
        updateForcePerBeat()
    }

    constructor(playerCount: Int) : this() {
        for(i in 0 until playerCount) {
            playerArray  += Player()
            forcePerBeat += playerArray[i].forcePerBeat
            currentForce += playerArray[i].currentForce
        }
        updateForcePerBeat()
    }

    private fun updateForcePerBeat() {
        var playerForce = 0
        playerArray.forEach { player -> playerForce += player.forcePerBeat }
        forcePerBeat = playerForce
    }

    fun changeForce(force: Int): Int {
        currentForce += force
        return currentForce
    }

    fun endOfBeatForce(): Int {
        currentForce += forcePerBeat
        if (currentForce > maxForce) currentForce = maxForce
        return forcePerBeat
    }
}