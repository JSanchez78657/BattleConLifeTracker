package com.example.battleconlifetracker.model.player

class SuperPlayer : Player() {

    init {
        currentLife = 30
        currentForce = 5
        maxForce = 20
        changeHealth(0)
    }

    override fun overloadAvailable(name: String): Boolean {
        if(currentForce < 2) return false
        return if(overloadsAvailable.containsKey(name) && overloadsAvailable[name] != null)
            overloadsAvailable[name]!! < 2
        else
            false
    }

    override fun changeHealth(life: Int): Int {
        //7 or less life
        if(currentLife + life <= 7)
            forcePerBeat = 4
        //8 to 15 life
        if(currentLife + life in 8..15)
            forcePerBeat = 3
        //16 or more life
        if(currentLife + life >= 16)
            forcePerBeat = 2
        currentLife += life
        return currentLife
    }
}