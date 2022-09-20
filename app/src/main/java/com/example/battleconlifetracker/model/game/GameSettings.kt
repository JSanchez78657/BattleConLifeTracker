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
            GameFlags.BOSS_VS_2 -> Pair(1, 2)
            GameFlags.BOSS_VS_3 -> Pair(1, 3)
            GameFlags.BOSS_VS_4 -> Pair(1, 4)
            else -> Pair(1,1)
        }
    }

    public fun gameSize(): Int { return teamSizes.first + teamSizes.second }
}