package com.example.suai_pocket_light

import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.request.get
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json

private const val GUAP_API_URI_BASE = "https://api.guap.ru/rasp/custom/get-sem-rasp/group"

object DataParser {

    fun parseSubjects(): List<List<Subject>>{
        var subjects: List<Subject> = getSUAIScheduleList().map { Subject(it) }
        val grouppedSubjects: MutableList<MutableList<Subject>> = mutableListOf()
        subjects = subjects.sortedBy { it.week }
        grouppedSubjects.add(mutableListOf(subjects[0]))
        var ind = 0
        for(i in 1..<subjects.size){
            if (subjects[i].day == subjects[i-1].day && subjects[i].week == subjects[i-1].week) grouppedSubjects[ind].add(subjects[i])
            else {
                grouppedSubjects.add(mutableListOf(subjects[i]))
                ind++
            }
        }
        return grouppedSubjects
    }

    private fun getSUAIScheduleList(group: String = "316"): List<SUAIRaspElement>{
        var raspDays: List<SUAIRaspElement>
        runBlocking {
            raspDays = async {
                raspDays = Json.decodeFromString<List<SUAIRaspElement>>(getTextApi(group))
                raspDays.forEach { println(it) }
                raspDays
            }.await()
        }
        return raspDays
    }

    private suspend fun getTextApi(group: String): String {
        val cl = HttpClient(CIO);
        return cl.get<String>("$GUAP_API_URI_BASE$group").replace("null", "\"\"")
    }
}