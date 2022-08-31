package com.example.battleconlifetracker.model.player

import java.io.Serializable

abstract class Player(var currentLife: Int = -1, var currentForce: Int = -1 , var maxForce: Int = -1) : Serializable {

    protected val overloadsAvailable: HashMap<String, Int> = hashMapOf("POWER" to 0, "GUARD" to 0, "PRIORITY" to 0)
    protected var finisherUsed = false
    var forcePerBeat = -1
        protected set


    abstract fun changeHealth(life: Int): Int

    abstract fun overloadAvailable(name: String): Boolean

    fun useOverload(name: String): Int {
        overloadsAvailable[name] = overloadsAvailable[name]!! + 1
        currentForce -= 2
        return currentForce
    }

    fun resetOverloads() {
        for(key in overloadsAvailable.keys)
            overloadsAvailable[key] = 0
    }

    fun changeForce(force: Int): Int {
        currentForce += force
        return currentForce
    }

    fun useFinisher(): Int {
        currentForce -= currentLife
        finisherUsed = true
        return currentForce
    }

    fun getAvailableActions(): List<String> {
        val actions: MutableList<String> = mutableListOf()
        if(finisherAvailable())
            actions.add("FINISHER")
        if(currentForce < 2) return actions.toList()
        for(key in overloadsAvailable.keys)
            if(overloadAvailable(key))
                actions.add(key)
        return actions.toList()
    }

    fun endOfBeatForce(): Int {
        currentForce += forcePerBeat
        if (currentForce > maxForce) currentForce = maxForce
        return forcePerBeat
    }

    private fun finisherAvailable(): Boolean {
        return !finisherUsed && currentForce >= currentLife
    }
}