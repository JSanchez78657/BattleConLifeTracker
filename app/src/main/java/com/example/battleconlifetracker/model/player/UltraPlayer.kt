package com.example.battleconlifetracker.model.player

class UltraPlayer : Player() {

    init {
        currentHealth = 40
        startingForce = 8
    }

    override fun overloadAvailable(name: String): Boolean {
//        if(currentForce < 2) return false
        return if(overloadsAvailable.containsKey(name) && overloadsAvailable[name] != null)
            overloadsAvailable[name]!! < 3
        else
            false
    }

    override fun updateForcePerBeat(newHealth: Int) {
        //10 or less life
        if(newHealth <= 10)
            forcePerBeat = 6
        //11 to 20 life
        if(newHealth in 11..20)
            forcePerBeat = 5
        //21 or more life
        if(newHealth >= 21)
            forcePerBeat = 4
    }
}