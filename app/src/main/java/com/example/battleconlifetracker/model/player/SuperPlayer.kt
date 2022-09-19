package com.example.battleconlifetracker.model.player

class SuperPlayer : Player() {

    init {
        maxHealth = 30
        currentHealth = maxHealth
        startingForce = 5
    }

    override fun overloadAvailable(name: String): Boolean {
        return if(overloadsAvailable.containsKey(name) && overloadsAvailable[name] != null)
            overloadsAvailable[name]!! < 2
        else
            false
    }

    override fun updateForcePerBeat(newHealth: Int) {
        //7 or less life
        if(newHealth <= 7)
            forcePerBeat = 4
        //8 to 15 life
        if(newHealth in 8..15)
            forcePerBeat = 3
        //16 or more life
        if(newHealth >= 16)
            forcePerBeat = 2
    }
}