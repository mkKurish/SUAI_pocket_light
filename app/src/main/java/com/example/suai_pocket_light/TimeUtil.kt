package com.example.suai_pocket_light

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date

object TimeUtil {
    private val today: Calendar = run {
        val temp = Calendar.getInstance()
        temp.setTimeInMillis(System.currentTimeMillis())
        temp
    }
    private val fistStudyDay = run {
        val temp = Calendar.getInstance()
        temp.set(
            today.get(Calendar.YEAR),
            8,
            1
        )
        temp
    }
    val curWeekType: Byte
        // Example: нечетная
        get() = if ((today.get(Calendar.WEEK_OF_YEAR) - fistStudyDay.get(Calendar.WEEK_OF_YEAR)) % 2 == 0) 1 else 2
    val curWeekTypeName: String
        // Example: нечетная
        get() = if ((today.get(Calendar.WEEK_OF_YEAR) - fistStudyDay.get(Calendar.WEEK_OF_YEAR)) % 2 == 0) "нечетная" else "четная"
    val curDay: String
        // Example: 12 сентября
        @SuppressLint("SimpleDateFormat")
        get() = SimpleDateFormat("d MMMM").format(Date(today.timeInMillis))
    val curWeekday: Byte
        // Example: понедельник
        @SuppressLint("SimpleDateFormat")
        get() = today.get(Calendar.DAY_OF_WEEK).toByte()
    val curWeekdayName: String
        // Example: понедельник
        @SuppressLint("SimpleDateFormat")
        get() = SimpleDateFormat("EEEE").format(Date(today.timeInMillis))
}