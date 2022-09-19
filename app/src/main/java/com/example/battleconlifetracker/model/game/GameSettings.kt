package com.example.battleconlifetracker.model.game

import java.io.Serializable

class GameSettings(val gameMode: GameFlags) : Serializable {
    public var teamSizes: Pair<Int, Int>

    init {
        teamSizes = when(gameMode) {
            GameFlags.DUEL -> Pair(1, 1)
            GameFlags.SUPER -> Pair(1, 2)
            GameFlags.ULTRA -> Pair(1, 3)
            GameFlags.TEAMS -> Pair(2, 2)
            else -> Pair(1,1)
        }
    }

    constructor(gameMode: GameFlags, playerCount: Int) : this(GameFlags.BOSS) {
        if(gameMode == GameFlags.BOSS) teamSizes = Pair(1, playerCount)
        else GameSettings(gameMode)
    }

    public fun gameSize(): Int { return teamSizes.first + teamSizes.second }
}