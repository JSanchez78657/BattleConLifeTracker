package com.example.battleconlifetracker.model.game

import com.example.battleconlifetracker.model.Team
import com.example.battleconlifetracker.model.player.NormalPlayer
import com.example.battleconlifetracker.model.player.Player

class TeamGame {

    val teamArray = arrayOf(Team(NormalPlayer(), NormalPlayer()), Team(NormalPlayer(), NormalPlayer()))
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

    fun checkAvailableActions(): List<List<String>>? {
        //TODO
        return null
    }

}