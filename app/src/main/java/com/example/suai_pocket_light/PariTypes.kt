package com.example.suai_pocket_light

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.example.suai_pocket_light.ui.theme.CustomTheme

enum class PariTypes(val paraType: String) {
    LABA("Лабораторная работа"),
    PRAKTIKA("Практическая работа"),
    LEKCIA("Лекция"),
    KURSACH("Курсовая работа"),
    UNKNOWN("------ -----");

    companion object {
        fun setParaType(value: String) = when (value) {
            "ЛР" -> LABA
            "ПР" -> PRAKTIKA
            "Л" -> LEKCIA
            "КР" -> KURSACH
            else -> UNKNOWN
        }

        @Composable
        fun getTypeColor(pt: PariTypes) = when (pt) {
            LABA -> CustomTheme.colors.labText
            PRAKTIKA -> CustomTheme.colors.praktText
            LEKCIA -> CustomTheme.colors.lekcText
            KURSACH -> CustomTheme.colors.kursText
            UNKNOWN -> CustomTheme.colors.secondaryText
        }
    }
}
