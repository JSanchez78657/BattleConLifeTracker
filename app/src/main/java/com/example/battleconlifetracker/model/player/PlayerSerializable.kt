package com.example.battleconlifetracker.model.player

import java.io.Serializable
import kotlin.properties.Delegates

class PlayerSerializable(
    var maxHealth: Int,
    var currentHealth: Int,
    var currentForce: Int,
    var forcePerBeat: Int,
    var overloadsAvailable: HashMap<String, Int>,
    var finisherUsed: Boolean,
    var finisherAvailable: Boolean
) : Serializable