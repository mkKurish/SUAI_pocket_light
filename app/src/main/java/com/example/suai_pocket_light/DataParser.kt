package com.example.suai_pocket_light

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.widget.Toast
import com.example.suai_pocket_light.TimeUtil.curWeekType
import com.example.suai_pocket_light.TimeUtil.curWeekday
import com.example.suai_pocket_light.TimeUtil.today
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.request.get
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json

private const val GUAP_API_URI_BASE = "https://api.guap.ru/rasp/custom/get-sem-rasp/group"
private const val STORED_SCHEDULE_FILE = "StoredSchedule"

object DataParser {

    fun parseSubjects(appContext: Context): List<List<Subject>> {
        val subjects: List<Subject> =
            getSUAIScheduleList(appContext).map { Subject(it) }.sortedBy { it.para.order }.sortedBy { it.day }
                .sortedBy { it.week }
        val grouppedSubjects: MutableList<MutableList<Subject>> =
            mutableListOf(mutableListOf(subjects[0]))
        var ind = 0
        for (i in 1..<subjects.size) {
            if (subjects[i].day == subjects[i - 1].day && subjects[i].week == subjects[i - 1].week) grouppedSubjects[ind].add(
                subjects[i]
            )
            else {
                grouppedSubjects.add(mutableListOf(subjects[i]))
                ind++
            }
        }

        val shiftedSubjects: MutableList<MutableList<Subject>> = mutableListOf()
        var indInsertion: Int = grouppedSubjects.size
        var voidGroup: MutableList<Subject> = mutableListOf()
        for (group in grouppedSubjects) {
            if (group[0].week == curWeekType(today) && group[0].day == curWeekday(today)) {
                indInsertion = grouppedSubjects.indexOf(group)
                shiftedSubjects.add(group)
            } else if (indInsertion != grouppedSubjects.size) {
                if (group[0].para.order == 0.toByte())
                    voidGroup = group
                else
                    shiftedSubjects.add(group)
            }
        }
        for (i in 0..<indInsertion) {
            if (grouppedSubjects[i][0].para.order == 0.toByte())
                voidGroup = grouppedSubjects[i]
            else
                shiftedSubjects.add(grouppedSubjects[i])
        }
        if (voidGroup.size != 0) shiftedSubjects.add(voidGroup)


        val withEmptySubjects: MutableList<MutableList<Subject>> = mutableListOf()
        var temp = 0
        for (i in curWeekday(today)..7) {
            if (temp < shiftedSubjects.size) {
                if (shiftedSubjects[temp][0].day == i.toByte()) {
                    withEmptySubjects.add(shiftedSubjects[temp])
                    temp++
                } else
                    withEmptySubjects.add(mutableListOf())
            }
        }
        for (i in 1..7) {
            if (temp < shiftedSubjects.size) {
                if (shiftedSubjects[temp][0].day == i.toByte()) {
                    withEmptySubjects.add(shiftedSubjects[temp])
                    temp++
                } else
                    withEmptySubjects.add(mutableListOf())
            }
        }
        for (i in 1..<curWeekday(today)) {
            if (temp < shiftedSubjects.size) {
                if (shiftedSubjects[temp][0].day == i.toByte()) {
                    withEmptySubjects.add(shiftedSubjects[temp])
                    temp++
                } else
                    withEmptySubjects.add(mutableListOf())
            }
        }


        return withEmptySubjects
    }

    private fun getSUAIScheduleList(
        appContext: Context,
        group: String = "316"
    ): List<SUAIRaspElement> {
        var raspDays: List<SUAIRaspElement>
        if (checkInternet(appContext)) {
            runBlocking {
                raspDays = async {
                    Json.decodeFromString<List<SUAIRaspElement>>(getTextApi(appContext, group))
                }.await()
            }
        }else{
            Toast.makeText(appContext, "Не удалось обновить расписание", Toast.LENGTH_SHORT)
            raspDays = Json.decodeFromString<List<SUAIRaspElement>>(getStoredTextApi(appContext))
        }
        return raspDays
    }

    private suspend fun getTextApi(appContext: Context, group: String): String {
        val cl = HttpClient(CIO)
        val fileContents = cl.get<String>("$GUAP_API_URI_BASE$group").replace("null", "\"\"")
        appContext.openFileOutput(STORED_SCHEDULE_FILE, Context.MODE_PRIVATE).use {
            it.write(fileContents.toByteArray())
        }
        return fileContents
    }

    private fun getStoredTextApi(ctxt: Context): String {
        val cl = HttpClient(CIO)
        return ctxt.openFileInput(STORED_SCHEDULE_FILE).bufferedReader().readText()
    }

    private fun checkInternet(appContext: Context): Boolean {
        val connectivityManager =
            appContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = connectivityManager.activeNetwork ?: return false
        val activeNetwork = connectivityManager.getNetworkCapabilities(network) ?: return false
        return when {
            activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            else -> false
        }
    }
}