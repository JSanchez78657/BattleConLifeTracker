package com.example.battleconlifetracker.model.game

import com.example.battleconlifetracker.model.Team

open class TeamGame {

    val teamMap = hashMapOf<Int, Team>()
    val forcePool by lazy { calcForcePool() }
    var forceGained = 0
    var initialized = false

    private fun calcForcePool(): Int {
        var numPlayers = 0
        teamMap.forEach { (_, team) -> numPlayers += team.playerList.size }
        return 5 + 20 * numPlayers
    }

    fun addTeam(teamId: Int, teamSize: Int) {
        if(initialized) return
        teamMap[teamId] = Team(teamSize)
    }

    fun initGame() {
        teamMap.forEach { (_, team) ->
            team.playerList.forEach { player ->
                forceGained += player.startingForce
            }
        }
        calcForcePool()
    }

    fun endBeat() : Boolean {
        teamMap.forEach { (_, team) -> forceGained += team.endBeat() }
        return forceGained >= forcePool
    }

    fun getAvailableActions(): HashMap<Int, HashMap<Int, List<String>>> {
        val actionsList: HashMap<Int, HashMap<Int, List<String>>> = hashMapOf()
        teamMap.forEach { (key, team) ->
            actionsList[key] = team.getAvailableActions()
        }
        return actionsList
    }

    fun changeHealth(teamId: Int, playerId: Int, life: Int) {
        teamMap[teamId]?.playerList?.get(playerId)?.changeHealth(life)!!
    }

    fun changeForce(teamId: Int, force: Int) {
        teamMap[teamId]?.changeForce(force)!!
    }

    fun useFinisher(teamId: Int, playerId: Int) {
        teamMap[teamId]?.useFinisher(playerId)
    }

    fun useOverload(overload: String, teamId: Int, playerId: Int) {
        teamMap[teamId]?.useOverload(overload, playerId)
    }

    fun getRemainingForce(): Int {
        return if (forceGained > forcePool) 0 else forcePool - forceGained
    }
}