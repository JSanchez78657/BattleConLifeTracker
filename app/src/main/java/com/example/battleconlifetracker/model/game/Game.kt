package com.example.battleconlifetracker.model.game

import android.os.Parcel
import android.os.Parcelable
import com.example.battleconlifetracker.model.GameSettings
import com.example.battleconlifetracker.model.player.NormalPlayer
import com.example.battleconlifetracker.model.player.Player
import java.io.Serializable

class Game(private val settings: GameSettings) {

    private var forceGained: Int = settings.getStartingForce() * settings.getNumPlayers()
    private val players: Array<Player> = Array(settings.getNumPlayers()) { NormalPlayer() }

    fun endBeat(): Boolean {
        players.forEach { p -> forceGained += p.endOfBeatForce(); p.resetOverloads() }
        return forceGained >= settings.getMaxForce()
    }

    fun changeHealth(id: Int, health: Int): Int {
        return players[id].changeHealth(health)
    }

    fun changeForce(id: Int, force: Int): Int {
        return players[id].changeForce(force)
    }

    fun useFinisher(id: Int): Int {
        return players[id].useFinisher()
    }

    fun checkAvailableActions(): List<List<String>> {
        val playerActions: MutableList<List<String>> = mutableListOf()
        for(player in players) {
            playerActions.add(player.getAvailableActions())
        }
        return playerActions.toList()
    }

    fun getPlayer(id: Int): Player? {
        if (id >= players.size || id < 0)
            return null
        return players[id]
    }

    fun getPlayerCount(): Int {
        return settings.getNumPlayers()
    }

    fun overloadAvailable(id: Int, name: String): Boolean {
        return players[id].overloadAvailable(name)
    }

    fun useOverload(id: Int, name: String): Int {
        return players[id].useOverload(name)
    }

    fun resetOverloads() {
        for(player in players)
            player.resetOverloads()
    }

    fun getSettings() : GameSettings {
        return settings
    }

    fun getRemainingForce(): Int {
        return if (forceGained > settings.getMaxForce()) 0 else settings.getMaxForce() - forceGained
    }
}