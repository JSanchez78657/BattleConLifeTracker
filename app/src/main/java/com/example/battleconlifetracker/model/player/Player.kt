package com.example.battleconlifetracker.model.player

import kotlin.properties.Delegates

open class Player() {

    var currentHealth: Int by Delegates.observable(20) { _, _, newValue -> healthChanged(newValue) }
    var maxHealth: Int = 20
    var currentForce: Int = 2
    var maxForce: Int = 10
    var forcePerBeat = 1
        protected set
    protected var overloadsAvailable: HashMap<String, Int> = hashMapOf("POWER" to 0, "GUARD" to 0, "PRIORITY" to 0)
    protected var finisherUsed = false
    protected var finisherAvailable = true


    constructor(serializable: PlayerSerializable) : this() {
        this.currentHealth = serializable.currentHealth
        this.maxHealth = serializable.maxHealth
        this.currentForce = serializable.currentForce
        this.forcePerBeat = serializable.forcePerBeat
        this.overloadsAvailable = serializable.overloadsAvailable
        this.finisherUsed = serializable.finisherUsed
        this.finisherAvailable = serializable.finisherAvailable
    }

    open fun updateForcePerBeat(newHealth: Int) {
        //7 or less life
        if(newHealth <= 7)
            forcePerBeat = 2
        //8 or more life
        if(newHealth > 7)
            forcePerBeat = 1
    }

    open fun overloadAvailable(name: String): Boolean {
        if(currentForce < 2) return false
        return if(overloadsAvailable.containsKey(name) && overloadsAvailable[name] != null)
            overloadsAvailable[name]!! < 1
        else
            false
    }

    private fun healthChanged(newHealth: Int) {
        val health = if(newHealth > maxHealth) maxHealth else newHealth
        updateForcePerBeat(health)
        if(!finisherUsed) updateFinisherAvailable(health)
    }

    private fun updateFinisherAvailable(newHealth: Int) {
        finisherAvailable = !finisherUsed && newHealth <= currentForce
    }

    private fun finisherAvailable(): Boolean {
        return !finisherUsed && currentForce >= currentHealth
    }

    fun getSerializable(): PlayerSerializable {
        return PlayerSerializable(this.maxHealth, this.currentHealth, this.currentForce, this.forcePerBeat, this.overloadsAvailable, this.finisherUsed, this.finisherAvailable)
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
}