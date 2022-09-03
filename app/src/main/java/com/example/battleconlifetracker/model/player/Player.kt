package com.example.battleconlifetracker.model.player

import kotlin.properties.Delegates

abstract class Player {

    var currentHealth: Int by Delegates.observable(-1) { _, _, newValue -> healthChanged(newValue) }
    var currentForce: Int = -1
    var maxForce: Int = -1
    protected val overloadsAvailable: HashMap<String, Int> = hashMapOf("POWER" to 0, "GUARD" to 0, "PRIORITY" to 0)
    protected var finisherUsed = false
    protected var finisherAvailable = true
    var forcePerBeat = -1
        protected set

    private fun healthChanged(newHealth: Int) {
        updateForcePerBeat(newHealth)
        if(!finisherUsed) updateFinisherAvailable(newHealth)
    }

    abstract fun updateForcePerBeat(newHealth: Int)

    abstract fun overloadAvailable(name: String): Boolean

    private fun updateFinisherAvailable(newHealth: Int) {
        finisherAvailable = !finisherUsed && newHealth <= currentForce
    }

    fun useOverload(name: String): Int {
        overloadsAvailable[name] = overloadsAvailable[name]!! + 1
        currentForce -= 2
        return currentForce
    }

    fun resetOverloads() {
        for(key in overloadsAvailable.keys)
            overloadsAvailable[key] = 0
    }

    fun changeHealth(health: Int): Int {
        currentHealth += health
        return currentHealth
    }

    fun changeForce(force: Int): Int {
        currentForce += force
        return currentForce
    }

    fun useFinisher(): Int {
        currentForce -= currentHealth
        finisherUsed = true
        return currentForce
    }

    fun getAvailableActions(): List<String> {
        val actions: MutableList<String> = mutableListOf()
        if(finisherAvailable())
            actions.add("FINISHER")
        if(currentForce < 2) return actions.toList()
        overloadsAvailable.keys.forEach { key -> if(overloadAvailable(key)) actions.add(key) }
        return actions.toList()
    }

    fun endOfBeatForce(): Int {
        currentForce += forcePerBeat
        if (currentForce > maxForce) currentForce = maxForce
        return forcePerBeat
    }

    private fun finisherAvailable(): Boolean {
        return !finisherUsed && currentForce >= currentHealth
    }
}