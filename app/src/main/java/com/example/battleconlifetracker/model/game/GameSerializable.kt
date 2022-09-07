package com.example.battleconlifetracker.model.game

import com.example.battleconlifetracker.model.player.PlayerSerializable
import java.io.Serializable

class GameSerializable(
    var forceGained: Int,
    var players: Array<PlayerSerializable>
) : Serializable