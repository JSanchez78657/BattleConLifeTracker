package com.example.battleconlifetracker.model

import java.io.Serializable

class GameSettings(private var startingLife: Int, private var startingForce: Int, private var maxForce: Int, private var numPlayers: Int) : Serializable {

    private var forcePerBeat = 1
        get() = field
        set(value) {
            field = value
        }
    private var bossMode = false
        get() = field
        set(value) {
            field = value
        }
    private var teamsMode = false
        get() = field
        set(value) {
            field = value
        }

    fun getStartingLife() : Int {
        return startingLife
    }

    fun getStartingForce() : Int {
        return startingForce
    }

    fun getMaxForce() : Int {
        return maxForce
    }

    fun getNumPlayers() : Int {
        return numPlayers
    }

    fun setStartingHealth(health: Int) {
        startingLife = health
    }

    fun setStartingForce(force: Int) {
        startingForce = force
    }

    fun setMaxForce(force: Int) {
        maxForce = force
    }

    fun setNumPlayers(players: Int) {
        numPlayers = players
    }
}