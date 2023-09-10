package com.example.suai_pocket_light

enum class PariTimes(val start: String, val end: String, val order: Byte) {
    FIRST("9:30", "11:00", 1),
    SECOND("11:10", "12:40", 2),
    THIRD("13:00", "14:30", 3),
    FOURTH("15:00", "16:30", 4),
    FIFTH("16:40", "18:10", 5),
    SIXTH("18:30", "20:00", 6),
    UNDEFINED("--:--", "--:--", 0);

    companion object {
        fun setParaTime(num: Int) = when (num) {
            1 -> FIRST
            2 -> SECOND
            3 -> THIRD
            4 -> FOURTH
            5 -> FIFTH
            6 -> SIXTH
            else -> UNDEFINED
        }
    }
}