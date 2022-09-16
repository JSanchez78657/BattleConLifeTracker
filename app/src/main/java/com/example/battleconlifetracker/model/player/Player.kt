package com.example.battleconlifetracker.model.player

import kotlin.properties.Delegates

open class Player {

    var currentHealth: Int = 20
        set(newHealth) {
            val health = if(newHealth > maxHealth) maxHealth else newHealth
            updateForcePerBeat(health)
            field = health
        }
    var maxHealth: Int = 20
    var startingForce = 2
    var forcePerBeat = 1
        protected set
    protected var overloadsAvailable: HashMap<String, Int> = hashMapOf("POWER" to 0, "GUARD" to 0, "PRIORITY" to 0)
    protected var finisherUsed = false

    open fun updateForcePerBeat(newHealth: Int) {
        //7 or less life
        if(newHealth <= 7)
            forcePerBeat = 2
        //8 or more life
        if(newHealth > 7)
            forcePerBeat = 1
    }

    open fun overloadAvailable(name: String): Boolean {
        return if(overloadsAvailable.containsKey(name) && overloadsAvailable[name] != null)
            overloadsAvailable[name]!! < 1
        else
            false
    }

    fun useOverload(name: String) {
        overloadsAvailable[name] = overloadsAvailable[name]!! + 1
    }

    fun resetOverloads() {
        for(key in overloadsAvailable.keys)
            overloadsAvailable[key] = 0
    }

    fun changeHealth(health: Int): Int {
        currentHealth += health
        return currentHealth
    }

    fun useFinisher(): Int {
        finisherUsed = true
        return currentHealth
    }

    fun getAvailableActions(): MutableList<String> {
        val actions: MutableList<String> = mutableListOf()
        if(!finisherUsed)
            actions.add("FINISHER")
        overloadsAvailable.keys.forEach { key -> if(overloadAvailable(key)) actions.add(key) }
        return actions
    }
}