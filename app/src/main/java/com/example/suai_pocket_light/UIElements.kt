package com.example.suai_pocket_light

import android.content.res.Configuration
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.suai_pocket_light.PariTypes.Companion.getTypeColor
import com.example.suai_pocket_light.TimeUtil.curDay
import com.example.suai_pocket_light.TimeUtil.curWeekType
import com.example.suai_pocket_light.TimeUtil.curWeekTypeName
import com.example.suai_pocket_light.TimeUtil.curWeekdayName
import com.example.suai_pocket_light.ui.theme.CustomTheme
import com.example.suai_pocket_light.ui.theme.MainTheme
import com.example.suai_pocket_light.ui.theme.cornersRadius
import com.example.suai_pocket_light.ui.theme.spacingLarge
import com.example.suai_pocket_light.ui.theme.spacingMedium
import com.example.suai_pocket_light.ui.theme.spacingSmall

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ListOfSubjects(subjectsList: List<List<Subject>>) {
    val wktp = curWeekType


    LazyColumn(
        modifier = Modifier
            .padding(horizontal = spacingMedium, vertical = spacingSmall)
    ) {
        stickyHeader {
            CombinedWeekTypeLabel()
        }
        if (subjectsList.size > 1) {
            item {
                CombinedDateLabel()
                Subjects(subjectsList[0])
            }
        }
        for (i in 1..<(subjectsList.size - 1)) {
            item {
                SimpleDateLabel()
                Subjects(subjectsList[i])
            }
        }
        item {
            if (subjectsList.last()[0].para.order == 0.toByte()) {
                OutOfTheGridLabel()
            } else {
                SimpleDateLabel()
            }
            Subjects(subjectsList.last())
        }

    }
}

@Composable
private fun SubjectsContent(subjects: List<Subject>) {
    Column(modifier = Modifier.padding(spacingMedium)) {
        subjects.forEach {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(80.dp)
            ) {
                Row {
                    Box(
                        modifier = Modifier
                            .fillMaxHeight()
                            .padding(start = spacingMedium)
                    ) {
                        Column(
                            verticalArrangement = Arrangement.SpaceAround,
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier.fillMaxHeight()
                        ) {
                            Text(text = it.para.start, color = CustomTheme.colors.primaryText)
                            Text(
                                text = it.para.order.toString(),
                                color = CustomTheme.colors.secondaryText
                            )
                            Text(text = it.para.end, color = CustomTheme.colors.primaryText)
                        }
                    }
                    Spacer(modifier = Modifier.width(spacingMedium))
                    Card(
                        colors = CardDefaults.cardColors(CustomTheme.colors.secondaryText),
                        modifier = Modifier
                            .width(3.dp)
                            .fillMaxHeight()
                            .padding(vertical = spacingSmall),
                        shape = RoundedCornerShape(50.dp)
                    ) {}
                    Spacer(modifier = Modifier.width(spacingMedium))
                    Column(
                        verticalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxHeight()
                    ) {
                        Text(text = it.type.paraType, color = getTypeColor(it.type))
                        Text(
                            text = it.discidpline,
                            color = CustomTheme.colors.primaryText,
                            fontWeight = FontWeight.SemiBold
                        )
                        Row() {
                            Card(
                                shape = RoundedCornerShape(cornersRadius),
                                colors = CardDefaults.cardColors(CustomTheme.colors.additionalCard)
                            ) {
                                Text(
                                    modifier = Modifier.padding(horizontal = spacingSmall),
                                    text = it.room,
                                    fontSize = 10.sp,
                                    color = CustomTheme.colors.secondaryText
                                )

                            }
                        }
                        Text(
                            text = it.teacher,
                            color = CustomTheme.colors.secondaryText,
                            maxLines = 1
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(spacingMedium))
        }
    }
}

@Composable
private fun Subjects(
    subjects: List<Subject>
) {
    Card(
        shape = RoundedCornerShape(cornersRadius),
        colors = CardDefaults.cardColors(CustomTheme.colors.mainCard),
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp * subjects.size + spacingLarge * 2 + spacingMedium * (subjects.size - 1))
    ) {
        SubjectsContent(subjects)
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES, name = "Dark mode")
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO, name = "Light mode")
@Composable
private fun PreviewListOfSubjects() {
    MainTheme() {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(400.dp)
        ) {
            ListOfSubjects(
                listOf(
                    listOf(
                        Subject(
                            PariTimes.FIRST,
                            PariTypes.LABA,
                            "Алгоритмы и структуры данных",
                            "Б.М. 67 52-18",
                            "4231; 4232; 4233К; 4236",
                            "Матьяш В.А. — доцент, канд. техн. наук, доцент",
                            1,
                            2
                        ),
                        Subject(
                            PariTimes.SECOND,
                            PariTypes.KURSACH,
                            "Алгоритмы",
                            "Б.М. 67 23-08",
                            "4232",
                            "Матьяш В.А. — доцент",
                            1,
                            2
                        )
                    ),
                    listOf(
                        Subject(
                            PariTimes.FIFTH,
                            PariTypes.LEKCIA,
                            "Алгоритмы и структуры данных",
                            "Б.М. 67 52-18",
                            "4231; 4232; 4233К; 4236",
                            "Матьяш В.А. — доцент, канд. техн. наук, доцент",
                            1,
                            3
                        )
                    )
                )
            )
        }
    }
}

@Composable
private fun SimpleDateLabel() {
    // Вторник, 12 сентября
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(CustomTheme.colors.background)
    ) {
        Text(
            text = "${curWeekdayName.replaceFirstChar { it.uppercase() }}, ${curDay}",
            color = CustomTheme.colors.secondaryText
        )
    }
}

@Composable
private fun SimpleWeekTypeLabel() {
    // * Нечетная неделя
    val dotColor =
        if (curWeekType == 1.toByte()) CustomTheme.colors.oddWeek else CustomTheme.colors.evenWeek
    Row(
        verticalAlignment = Alignment.CenterVertically, modifier = Modifier
            .fillMaxWidth()
            .background(CustomTheme.colors.background)
    ) {
        Card(
            colors = CardDefaults.cardColors(dotColor),
            modifier = Modifier
                .width(8.dp)
                .height(8.dp)
        ) {}
        Spacer(modifier = Modifier.width(spacingSmall))
        Text(
            text = "${curWeekTypeName.replaceFirstChar { it.uppercase() }} неделя",
            color = CustomTheme.colors.secondaryText
        )
    }
}

@Composable
private fun CombinedDateLabel() {
    // Воскресенье, сегодня
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(CustomTheme.colors.background)
    ) {
        Text(
            text = "${curWeekdayName.replaceFirstChar { it.uppercase() }}, сегодня",
            color = CustomTheme.colors.secondaryText
        )
    }
}

@Composable
private fun CombinedWeekTypeLabel() {
    // * 10 сентября, четная неделя
    val dotColor =
        if (curWeekType == 1.toByte()) CustomTheme.colors.oddWeek else CustomTheme.colors.evenWeek
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .background(CustomTheme.colors.background)
    ) {
        Card(
            colors = CardDefaults.cardColors(dotColor),
            modifier = Modifier
                .width(8.dp)
                .height(8.dp)
        ) {}
        Spacer(modifier = Modifier.width(spacingSmall))
        Text(
            text = "$curDay, ${curWeekTypeName} неделя",
            color = CustomTheme.colors.secondaryText
        )
    }
}

@Composable
private fun OutOfTheGridLabel() {
    // Вне сетки
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(CustomTheme.colors.background)
    ) {
        Text(
            text = "Вне сетки",
            color = CustomTheme.colors.secondaryText
        )
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES, name = "Dark mode")
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO, name = "Light mode")
@Composable
fun LabelsPreview() {
    MainTheme {
        Column(Modifier.background(color = CustomTheme.colors.background)) {
            SimpleDateLabel()
            SimpleWeekTypeLabel()
            CombinedDateLabel()
            CombinedWeekTypeLabel()
            OutOfTheGridLabel()
        }
    }
}