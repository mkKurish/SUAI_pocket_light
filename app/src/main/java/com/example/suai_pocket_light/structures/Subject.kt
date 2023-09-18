package com.example.suai_pocket_light.structures

import com.example.suai_pocket_light.structures.PariTimes.Companion.setParaTime
import com.example.suai_pocket_light.structures.PariTypes.Companion.setParaType

data class Subject(
    val para: PariTimes,
    val type: PariTypes,
    val discipline: String,
    val room: String,
    val groups: String,
    val teacher: String,
    val week: Byte,
    val day: Byte
) {
    constructor(schedule: SUAIRaspElement) : this(
        setParaTime(schedule.Less),
        setParaType(schedule.Type),
        schedule.Disc,
        roomsParser(schedule.Rooms, schedule.Build),
        groupsParser(schedule.GroupsText),
        if (schedule.PrepsText == "") "Преподаватель не указан" else schedule.PrepsText,
        schedule.Week,
        schedule.Day
    )

    companion object {
        // "Build":"Б.Морская 67","Rooms":"52-18"
        private fun roomsParser(rms: String, bld: String) = when (bld) {
            "Б.Морская 67" -> "Б.М. $rms"
            "Ленсовета 14" -> "Ленс. $rms"
            "Гастелло 15" -> "Гаст. $rms"
            else -> "--- $rms"
        }

        // "315; 316; 317; 318" -> "315 | 316 | 317 | 318"
        private fun groupsParser(grps: String) = grps.replace("; ", " | ")
    }

}
