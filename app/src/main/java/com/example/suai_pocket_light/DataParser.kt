package com.example.suai_pocket_light

import com.example.suai_pocket_light.TimeUtil.curWeekType
import com.example.suai_pocket_light.TimeUtil.curWeekday
import com.example.suai_pocket_light.TimeUtil.curWeekdayName
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.request.get
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json

private const val GUAP_API_URI_BASE = "https://api.guap.ru/rasp/custom/get-sem-rasp/group"

object DataParser {

    fun parseSubjects(): List<List<Subject>>{
        val subjects: List<Subject> = getSUAIScheduleList().map { Subject(it) }.sortedBy { it.para.order }.sortedBy { it.day }.sortedBy { it.week }
        val grouppedSubjects: MutableList<MutableList<Subject>> = mutableListOf(mutableListOf(subjects[0]))
        var ind = 0
        for(i in 1..<subjects.size){
            if (subjects[i].day == subjects[i-1].day && subjects[i].week == subjects[i-1].week) grouppedSubjects[ind].add(subjects[i])
            else {
                grouppedSubjects.add(mutableListOf(subjects[i]))
                ind++
            }
        }
        val shiftedSubjects: MutableList<MutableList<Subject>> = mutableListOf()
        var indInsertion: Int = grouppedSubjects.size
        var voidGroup: MutableList<Subject> = mutableListOf()
        for (group in grouppedSubjects){
            if (group[0].week == curWeekType && group[0].day == curWeekday){
                indInsertion = grouppedSubjects.indexOf(group)
                shiftedSubjects.add(group)
            }else if(indInsertion != grouppedSubjects.size) {
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
        return shiftedSubjects
    }

    private fun getSUAIScheduleList(group: String = "316"): List<SUAIRaspElement>{
        var raspDays: List<SUAIRaspElement>
        runBlocking {
            raspDays = async {
                Json.decodeFromString<List<SUAIRaspElement>>(getTextApi(group))
            }.await()
        }
        return raspDays
    }

    private suspend fun getTextApi(group: String): String {
        val cl = HttpClient(CIO);
        return cl.get<String>("$GUAP_API_URI_BASE$group").replace("null", "\"\"")
    }
}