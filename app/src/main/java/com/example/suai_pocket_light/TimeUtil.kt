package com.example.suai_pocket_light

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

object TimeUtil {
    val today: Calendar = run {
        val temp = Calendar.getInstance()
        temp.timeInMillis = System.currentTimeMillis()
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

    @SuppressLint("SimpleDateFormat")
    fun printAllDate(calInst: Calendar){
        println(SimpleDateFormat("H:m:S d.MMMM.y").format(Date(calInst.timeInMillis)))
        println("curWeekType = ${curWeekType(calInst)} (${curWeekTypeName(calInst)})")
        println("curDay = ${curDay(calInst)}")
        println("curWeekdayName = ${curWeekdayName(calInst)} (${curWeekday(calInst)})")
    }

    fun curWeekType(calInst: Calendar): Byte =
        // Example: нечетная
        if ((calInst.get(Calendar.WEEK_OF_YEAR) - fistStudyDay.get(Calendar.WEEK_OF_YEAR)) % 2 == 0) 1 else 2
    fun curWeekTypeName(calInst: Calendar): String =
        // Example: нечетная
        if (curWeekType(calInst) == 1.toByte()) "нечетная" else "четная"
    @SuppressLint("SimpleDateFormat")
    fun curDay(calInst: Calendar): String =
        // Example: 12 сентября d MMMM
        SimpleDateFormat("d MMMM").format(Date(calInst.timeInMillis))
    fun curWeekday(calInst: Calendar): Byte =
        // Example: 1
        (if (calInst.get(Calendar.DAY_OF_WEEK) - 1 == 0) 7 else calInst.get(Calendar.DAY_OF_WEEK) - 1).toByte()
    @SuppressLint("SimpleDateFormat")
    fun curWeekdayName(calInst: Calendar): String =
        // Example: понедельник
        SimpleDateFormat("EEEE").format(Date(calInst.timeInMillis))

    fun nextDay(days: Int):Calendar {
        val calendarIter = Calendar.getInstance()
        calendarIter.timeInMillis = today.timeInMillis + days * 86400000
        return calendarIter
    }
}