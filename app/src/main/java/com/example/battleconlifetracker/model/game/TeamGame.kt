package com.example.battleconlifetracker.model.game

import com.example.battleconlifetracker.model.Team
import com.example.battleconlifetracker.model.player.NormalPlayer

class TeamGame {

    val teamArray = arrayOf(Team(NormalPlayer(), NormalPlayer()), Team(NormalPlayer(), NormalPlayer()))
    val forcePool = 45 + 20 * teamArray.size * teamArray.first().playerArray.size
    var forceGained = 0

    init {
        teamArray.forEach {
            team -> team.playerArray.forEach {
                player -> forceGained += player.currentForce
            }
        }
    }

    fun endBeat() {
        teamArray.forEach {
            team -> forceGained += team.endOfBeatForce()
        }
    }

    fun changeHealth(teamId: Int, playerId: Int, life: Int): Int {
        return teamArray[teamId].playerArray[playerId].changeHealth(life)
    }

    fun changeForce(teamId: Int, playerId: Int, force: Int): Int {
        return teamArray[teamId].playerArray[playerId].changeForce(force)
    }

    fun useFinisher(teamId: Int, playerId: Int): Int {
        return teamArray[teamId].playerArray[playerId].useFinisher()
    }

    fun checkAvailableActions(): List<List<List<String>>> {
        //For each entry in team array, add a list of player's lists of available actions.
        val availableActions: MutableList<List<List<String>>> = mutableListOf()
        var playerList: MutableList<List<String>>
        teamArray.forEach {
            team -> playerList = mutableListOf()
            team.playerArray.forEach {
                player -> playerList.add(player.getAvailableActions())
            }
            availableActions.add(playerList.toList())
        }
        return availableActions.toList()
    }

    fun getRemainingForce(): Int {
        return if (forceGained > forcePool) 0 else forcePool - forceGained
    }

}