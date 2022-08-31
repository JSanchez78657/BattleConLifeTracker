package com.example.battleconlifetracker.model.player

class UltraPlayer : Player() {

    init {
        currentLife = 40
        currentForce = 8
        maxForce = 20
        changeHealth(0)
    }

    override fun overloadAvailable(name: String): Boolean {
        if(currentForce < 2) return false
        return if(overloadsAvailable.containsKey(name) && overloadsAvailable[name] != null)
            overloadsAvailable[name]!! < 3
        else
            false
    }

    override fun changeHealth(life: Int): Int {
        //10 or less life
        if(currentLife + life <= 10)
            forcePerBeat = 6
        //11 to 20 life
        if(currentLife + life in 11..20)
            forcePerBeat = 5
        //21 or more life
        if(currentLife + life >= 21)
            forcePerBeat = 4
        currentLife += life
        return currentLife
    }
}