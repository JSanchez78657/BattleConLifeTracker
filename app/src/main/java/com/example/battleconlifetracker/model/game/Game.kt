package com.example.battleconlifetracker.model.game

import com.example.battleconlifetracker.model.player.Player
import com.example.battleconlifetracker.model.player.PlayerSerializable

open class Game() {

    private var forceGained: Int = 0
    private var forcePool: Int = 45
    private var players: Array<Player> = arrayOf(Player(), Player())

    init {
//        players.forEach { player -> forceGained += player.currentForce }
    }

    constructor(serializable: GameSerializable) : this() {
        this.forceGained = serializable.forceGained
        this.players = convertSerializable(serializable.players)
    }

    private fun convertPlayersToSerializable(): Array<PlayerSerializable> {
        var arr: Array<PlayerSerializable> = arrayOf()
//        players.forEach { player -> arr += player.getSerializable() }
        return arr
    }

    private fun convertSerializable(serializable: Array<PlayerSerializable>): Array<Player> {
        var arr: Array<Player> = arrayOf()
        serializable.forEach { player -> arr += Player(player) }
        return arr
    }

    open fun endBeat(): Boolean {
//        players.forEach { p -> forceGained += p.endOfBeatForce(); p.resetOverloads() }
        return forceGained >= forcePool
    }

    open fun checkAvailableActions(): List<List<String>> {
        val playerActions: MutableList<List<String>> = mutableListOf()
        for(player in players) {
            playerActions.add(player.getAvailableActions())
        }
        return playerActions.toList()
    }

    fun getSerializable(): GameSerializable {
        return GameSerializable(forceGained, convertPlayersToSerializable())
    }

    fun changeHealth(id: Int, health: Int): Int {
        return players[id].changeHealth(health)
    }

    fun changeForce(id: Int, force: Int): Int {
//        return players[id].changeForce(force)
        return 0
    }

    fun useFinisher(id: Int): Int {
        return players[id].useFinisher()
    }

    fun getPlayer(id: Int): Player? {
        if (id >= players.size || id < 0)
            return null
        return players[id]
    }

    fun getPlayerCount(): Int {
        return players.size
    }

    fun overloadAvailable(id: Int, name: String): Boolean {
        return players[id].overloadAvailable(name)
    }

    fun useOverload(id: Int, name: String): Int {
//        return players[id].useOverload(name)
        return 0
    }

    fun resetOverloads() {
        for(player in players)
            player.resetOverloads()
    }

    fun getRemainingForce(): Int {
        return if (forceGained > forcePool) 0 else forcePool - forceGained
    }
}