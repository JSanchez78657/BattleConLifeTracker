package com.example.battleconlifetracker.model.player

class BossPlayer(numPlayers: Int) : Player() {

    init {
        currentLife = 25 * numPlayers
        currentForce = 2
        maxForce = 20
        //Jank solution to make sure bosses can't use/be shown finishers
        finisherUsed = true
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