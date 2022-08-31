package com.example.battleconlifetracker.model.player

class NormalPlayer() : Player() {

    init {
        currentLife = 20
        currentForce = 2
        maxForce = 10
        changeHealth(0)
    }

    override fun overloadAvailable(name: String): Boolean {
        if(currentForce < 2) return false
        return if(overloadsAvailable.containsKey(name) && overloadsAvailable[name] != null)
            overloadsAvailable[name]!! < 1
        else
            false
    }

    override fun changeHealth(life: Int): Int {
        //7 or less life
        if(currentLife + life <= 7)
            forcePerBeat = 2
        //8 or more life
        if(currentLife + life > 7)
            forcePerBeat = 1
        currentLife += life
        return currentLife
    }

}