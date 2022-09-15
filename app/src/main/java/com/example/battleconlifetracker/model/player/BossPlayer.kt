package com.example.battleconlifetracker.model.player

class BossPlayer(numPlayers: Int) : Player() {

    init {
        currentHealth = 25 * numPlayers
        startingForce = 2
        //Jank solution to make sure bosses can't use/be shown finishers
        finisherUsed = true
    }

    override fun overloadAvailable(name: String): Boolean {
//        if(currentForce < 2) return false
        return if(overloadsAvailable.containsKey(name) && overloadsAvailable[name] != null)
            overloadsAvailable[name]!! < 1
        else
            false
    }

    override fun updateForcePerBeat(newHealth: Int) {
        //7 or less life
        if(newHealth <= 7)
            forcePerBeat = 2
        //8 or more life
        if(newHealth > 7)
            forcePerBeat = 1
    }
}