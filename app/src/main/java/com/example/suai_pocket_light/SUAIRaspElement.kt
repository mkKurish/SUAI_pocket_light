package com.example.suai_pocket_light

import kotlinx.serialization.*

@Serializable
data class SUAIRaspElement(
    val ItemId: Int,
    val Week: Byte,
    val Day: Byte,
    val Less: Int,
    val Build: String,
    val Rooms: String,
    val Disc: String,
    val Type: String,
    val Groups: String,
    val GroupsText: String,
    val Preps: String,
    val PrepsText: String,
    val Dept: String
)