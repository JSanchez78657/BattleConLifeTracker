package com.example.battleconlifetracker.model.game

import com.example.battleconlifetracker.model.Team
import com.example.battleconlifetracker.model.player.Player

open class TeamGame(aSize: Int, bSize: Int) {

    var teamArray = arrayOf(Team(aSize), Team(bSize))
    val forcePool by lazy { calcForcePool() }
    var forceGained = 0

    init {
        initForceGained()
    }

    private fun initForceGained() {
        teamArray.forEach { team ->
            team.playerArray.forEach { player ->
                forceGained += player.currentForce
            }
        }
    }

    private fun calcForcePool(): Int {
        var numPlayers = 0
        teamArray.forEach { team -> numPlayers += team.playerArray.size }
        return 5 + 20 * numPlayers
    }

    fun endBeat() : Boolean {
        teamArray.forEach {
            team -> forceGained += team.endOfBeatForce()
        }
        return forceGained >= forcePool
    }

    fun checkAvailableActions(): List<List<String>> {
        val playerList: MutableList<List<String>> = mutableListOf()
        teamArray.forEach { team ->
            team.playerArray.forEach {
                    player -> playerList.add(player.getAvailableActions())
            }
        }
        return playerList.toList()
    }

    fun changeHealth(teamId: Int, playerId: Int, life: Int): Int {
        return teamArray[teamId].playerArray[playerId].changeHealth(life)
    }

    fun changeForce(teamId: Int, force: Int): Int {
        return teamArray[teamId].changeForce(force)
    }

    fun useFinisher(teamId: Int, playerId: Int): Int {
        return teamArray[teamId].playerArray[playerId].useFinisher()
    }

    fun getRemainingForce(): Int {
        return if (forceGained > forcePool) 0 else forcePool - forceGained
    }
}