package com.example.suai_pocket_light

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.widget.Toast
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.suai_pocket_light.TimeUtil.curWeekType
import com.example.suai_pocket_light.TimeUtil.curWeekday
import com.example.suai_pocket_light.TimeUtil.today
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.request.get
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json

private const val GUAP_API_URI_BASE = "https://api.guap.ru/rasp/custom/get-sem-rasp/group"
private const val GUAP_API_URI_GROUPS = "https://api.guap.ru/rasp/custom/get-sem-groups"
private const val STORED_SCHEDULE_FILE = "StoredSchedule"
val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

object DataParser {

    fun parseSubjects(appContext: Context): List<List<Subject>> {
        val subjects: List<Subject> =
            getSUAIScheduleList(appContext).map { Subject(it) }.sortedBy { it.para.order }
                .sortedBy { it.day }
                .sortedBy { it.week } // Getting sorted lessons
        val grouppedSubjects: MutableList<MutableList<Subject>> =
            mutableListOf(mutableListOf(subjects[0]))
        var ind = 0
        for (i in 1..<subjects.size) { // Grouping lessons by days
            if (subjects[i].day == subjects[i - 1].day && subjects[i].week == subjects[i - 1].week) grouppedSubjects[ind].add(
                subjects[i]
            )
            else {
                grouppedSubjects.add(mutableListOf(subjects[i]))
                ind++
            }
        }

        val shiftedSubjects: MutableList<MutableList<Subject>> =
            mutableListOf() // Shifting list, so first day will be today
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

        val withEmptySubjects: MutableList<MutableList<Subject>> =
            mutableListOf() // Adding empty objects for days with zero lessons
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
        if (voidGroup.size != 0) withEmptySubjects.add(voidGroup)

        return withEmptySubjects
    }

    private fun getSUAIScheduleList(
        appContext: Context
    ): List<SUAIRaspElement> {
        var raspDays: List<SUAIRaspElement>
        if (checkInternet(appContext)) {
            runBlocking {
                raspDays = async {
                    Json.decodeFromString<List<SUAIRaspElement>>(getTextApi(appContext))
                }.await()
            }
        } else {
            Toast.makeText(appContext, "Не удалось обновить расписание", Toast.LENGTH_SHORT).show()
            raspDays = Json.decodeFromString<List<SUAIRaspElement>>(getStoredTextApi(appContext))
        }
        return raspDays
    }

    private suspend fun getTextApi(appContext: Context): String {
        val cl = HttpClient(CIO)
        val fileContents = cl.get<String>("$GUAP_API_URI_BASE${requiredGroup.ItemId}").replace("null", "\"\"")
        appContext.openFileOutput(STORED_SCHEDULE_FILE, Context.MODE_PRIVATE).use {
            it.write(fileContents.toByteArray())
        }
        return fileContents
    }

    private fun getStoredTextApi(appContext: Context): String {
        return appContext.openFileInput(STORED_SCHEDULE_FILE).bufferedReader().readText()
    }

    fun getGroups(appContext: Context): List<Group> {
        var receive: List<Group> = listOf()
        if (checkInternet(appContext)) {
            val cl = HttpClient(CIO)
            runBlocking {
                receive =
                    async { Json.decodeFromString<List<Group>>(cl.get<String>(GUAP_API_URI_GROUPS)) }.await()
            }
        } else Toast.makeText(appContext, "Не удалось получить список групп", Toast.LENGTH_SHORT)
            .show()
        return receive
    }

    fun checkInternet(appContext: Context): Boolean {
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

    suspend fun usePrefGroup(appContext: Context){
        val dsIDk = intPreferencesKey("GroupId")
        val dsNamek = stringPreferencesKey("GroupName")
        val preferences = appContext.dataStore.data.first()
        requiredGroup = Group(preferences[dsNamek]?:"Выбор группы", preferences[dsIDk]?:1)
        subjectsList = parseSubjects(appContext)
    }

    suspend fun setPrefGroup(appContext: Context){
        val dsIDk = intPreferencesKey("GroupId")
        val dsNamek = stringPreferencesKey("GroupName")
        appContext.dataStore.edit {
            it[dsIDk] = requiredGroup.ItemId
            it[dsNamek] = requiredGroup.Name
        }
    }
}