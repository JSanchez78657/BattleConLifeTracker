package com.example.battleconlifetracker.model

import android.view.View
import com.example.battleconlifetracker.model.player.Player

class Team (vararg players: Player) {

    private val maxForce = 20
    private var currentForce = 0
    private var forcePerBeat = 0
    var playerArray: Array<Player> = arrayOf()

    init {
        for(i in players.indices) {
            playerArray[i] = players[i]
            forcePerBeat += players[i].forcePerBeat
        }
        updateForcePerBeat()
    }

    constructor(playerCount: Int) : this() {
        for(i in 0 until playerCount) {
            playerArray[i] = Player()
            forcePerBeat += playerArray[i].forcePerBeat
        }
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