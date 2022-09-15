package com.example.battleconlifetracker.model

import com.example.battleconlifetracker.model.player.Player

class Team (vararg players: Player) {

    private var maxForce = 0
    private var forcePerBeat = 0
    var currentForce = 0
    var playerList: MutableList<Player> = mutableListOf()

    init {
        for(i in players.indices) {
            playerList += players[i]
            forcePerBeat += players[i].forcePerBeat
            currentForce += players[i].startingForce
        }
        maxForce = if(playerList.size > 1) 20 else 10
        updateForcePerBeat()
    }

    constructor(playerCount: Int) : this() {
        for(i in 0 until playerCount) {
            playerList  += Player()
            forcePerBeat += playerList[i].forcePerBeat
            currentForce += playerList[i].startingForce
        }
        updateForcePerBeat()
    }

    private fun updateForcePerBeat() {
        var playerForce = 0
        playerList.forEach { player -> playerForce += player.forcePerBeat }
        forcePerBeat = playerForce
    }

    fun getAvailableActions(): HashMap<Int, List<String>> {
        val playerActions = hashMapOf<Int, List<String>>()
        var hold: List<String>
        for(i in playerList.indices) {
            hold = playerList[i].getAvailableActions()
            if(currentForce < playerList[i].currentHealth)
                hold.remove("FINISHER")
            if(currentForce < 2)
                hold.removeAll(listOf("POWER", "GUARD", "PRIORITY"))
            playerActions[i] = hold.toList()
        }
        return playerActions
    }

    fun useFinisher(playerId: Int) {
        currentForce -= playerList[playerId].useFinisher()
    }

    fun useOverload(overload: String, playerId: Int) {
        currentForce -= 2
        playerList[playerId].useOverload(overload)
    }

    fun changeForce(force: Int): Int {
        currentForce += force
        return currentForce
    }

    fun endBeat(): Int {
        updateForcePerBeat()
        currentForce += forcePerBeat
        if (currentForce > maxForce) currentForce = maxForce
        playerList.forEach{ player -> player.resetOverloads() }
        return forcePerBeat
    }
}