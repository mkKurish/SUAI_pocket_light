package com.example.suai_pocket_light

import android.content.res.Configuration
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.times
import com.example.suai_pocket_light.PariTypes.Companion.getTypeColor
import com.example.suai_pocket_light.TimeUtil.curDay
import com.example.suai_pocket_light.TimeUtil.curWeekType
import com.example.suai_pocket_light.TimeUtil.curWeekTypeName
import com.example.suai_pocket_light.TimeUtil.curWeekdayName
import com.example.suai_pocket_light.TimeUtil.nextDay
import com.example.suai_pocket_light.TimeUtil.today
import com.example.suai_pocket_light.ui.theme.CustomTheme
import com.example.suai_pocket_light.ui.theme.MainTheme
import com.example.suai_pocket_light.ui.theme.cornersRadius
import com.example.suai_pocket_light.ui.theme.spacingLarge
import com.example.suai_pocket_light.ui.theme.spacingMedium
import com.example.suai_pocket_light.ui.theme.spacingSmall

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ListOfSubjects(subjectsList: List<List<Subject>>) {
    LazyColumn(
        modifier = Modifier
            .padding(horizontal = spacingMedium)
    ) {
        stickyHeader {
            CombinedWeekTypeLabel()
        }
        if (subjectsList.isNotEmpty()) {
            item {
                CombinedDateLabel()
                SubjectsListElement(subjectsList[0], curWeekdayName(nextDay(0)))
            }
            for (i in 1..<subjectsList.size) {
                if (curWeekType(nextDay(i)) != curWeekType(nextDay(i - 1))) {
                    stickyHeader {
                        SimpleWeekTypeLabel(
                            curWeekType(nextDay(i)),
                            curWeekTypeName(nextDay(i))
                        )
                    }
                }
                item {
                    if (subjectsList[i].isNotEmpty()) {
                        if (subjectsList[i][0].para.order == 0.toByte()) {
                            OutOfTheGridLabel()
                        } else {
                            SimpleDateLabel(curWeekdayName(nextDay(i)), curDay(nextDay(i)))
                        }
                    } else {
                        SimpleDateLabel(curWeekdayName(nextDay(i)), curDay(nextDay(i)))
                    }
                    SubjectsListElement(subjectsList[i], curWeekdayName(nextDay(i)))
                }
            }
        }
    }
}

@Composable
private fun SubjectsListElement(subjects: List<Subject>, dow: String = "NONE") {
    if (subjects.isEmpty()) {
        EmptyElement(dow)
    } else {
        Subjects(subjects)
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
    ) {
        SubjectsContent(subjects)
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
                Row(modifier = Modifier.padding(top = spacingSmall)) {
                    Spacer(modifier = Modifier.width(spacingMedium))
                    Box(
                        modifier = Modifier
                            .fillMaxHeight()
                    ) {
                        Column(
                            verticalArrangement = Arrangement.SpaceAround,
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier
                                .fillMaxHeight()
                                .width(40.dp)
                        ) {
                            Text(
                                text = it.para.start, color = CustomTheme.colors.primaryText,
                                fontWeight = FontWeight.SemiBold
                            )
                            Text(
                                text = it.para.order.toString(),
                                color = CustomTheme.colors.secondaryText,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = it.para.end, color = CustomTheme.colors.primaryText,
                                fontSize = 12.sp
                            )
                        }
                    }
                    Spacer(modifier = Modifier.width(spacingMedium))
                    Card(
                        colors = CardDefaults.cardColors(CustomTheme.colors.ghostElement),
                        modifier = Modifier
                            .width(3.dp)
                            .fillMaxHeight(),
                        shape = RoundedCornerShape(50.dp)
                    ) {}
                    Spacer(modifier = Modifier.width(spacingMedium))
                    Column(
                        verticalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxHeight()
                    ) {
                        Text(
                            text = it.type.paraType, color = getTypeColor(it.type),
                            fontWeight = FontWeight.Medium
                        )
                        Text(
                            text = it.discipline,
                            color = CustomTheme.colors.primaryText,
                            fontWeight = FontWeight.Bold,
                            maxLines = 1
                        )
                        Row {
                            AdditionalCard(it.room)
                            Spacer(modifier = Modifier.width(spacingSmall))
                            AdditionalCard(it.groups)
                        }
                        Text(
                            text = it.teacher,
                            color = CustomTheme.colors.secondaryText,
                            maxLines = 1
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(spacingSmall))
        }
    }
}

@Composable
private fun AdditionalCard(content: String) {
    Card(
        shape = RoundedCornerShape(cornersRadius),
        colors = CardDefaults.cardColors(CustomTheme.colors.additionalCard)
    ) {
        Text(
            modifier = Modifier.padding(horizontal = 5.dp, vertical = 2.dp),
            text = content,
            fontSize = 12.sp,
            color = CustomTheme.colors.secondaryText,
            maxLines = 1
        )
    }
}

@Composable
private fun EmptyElement(dow: String) {
    Card(
        shape = RoundedCornerShape(cornersRadius),
        colors = CardDefaults.cardColors(CustomTheme.colors.mainCard),
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(vertical = spacingMedium, horizontal = spacingLarge)
        ) {
            Image(
                painter = painterResource(CustomTheme.images.calendar_pic),
                contentDescription = "icon",
                contentScale = ContentScale.Inside,
                modifier = Modifier.size(35.dp)
            )
            Spacer(modifier = Modifier.width(spacingLarge))
            Column {
                Text(
                    text = "$dow, пар нет!", color = CustomTheme.colors.primaryText,
                    fontWeight = FontWeight.SemiBold
                )
                Spacer(modifier = Modifier.height(spacingSmall))
                Text(text = "Можно спать спокойно!", color = CustomTheme.colors.secondaryText)
            }
        }
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES, name = "Dark mode")
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO, name = "Light mode")
@Composable
private fun PreviewListOfSubjects() {
    MainTheme() {
        ListOfSubjects(
            listOf(
                listOf(
                    Subject(
                        PariTimes.FIRST,
                        PariTypes.LABA,
                        "Алгоритмы и структуры данных",
                        "Б.М. 52-18",
                        "4231; 4232; 4233К; 4236",
                        "Матьяш В.А. — доцент, канд. техн. наук, доцент",
                        1,
                        2
                    ),
                    Subject(
                        PariTimes.SECOND,
                        PariTypes.KURSACH,
                        "Алгоритмы",
                        "Гаст. 23-08",
                        "4232",
                        "Матьяш В.А. — доцент",
                        1,
                        2
                    )
                ),
                listOf(
                    Subject(
                        PariTimes.FIFTH,
                        PariTypes.PRAKTIKA,
                        "Алгоритмы и структуры данных",
                        "Б.М. 52-18",
                        "4231; 4232; 4233К; 4236",
                        "Матьяш В.А. — доцент, канд. техн. наук, доцент",
                        1,
                        3
                    )
                ),
                listOf(),
                listOf(
                    Subject(
                        PariTimes.FOURTH,
                        PariTypes.LABA,
                        "Алгоритмы и структуры данных",
                        "Б.М. 52-18",
                        "4231; 4232; 4233К; 4236",
                        "Матьяш В.А. — доцент, канд. техн. наук, доцент",
                        1,
                        3
                    )
                ),
                listOf(
                    Subject(
                        PariTimes.SIXTH,
                        PariTypes.LEKCIA,
                        "Алгоритмы и структуры данных",
                        "Б.М. 52-18",
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


@Composable
private fun SimpleDateLabel(cwdn: String = curWeekdayName(today), cd: String = curDay(today)) {
    // Вторник, 12 сентября
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(CustomTheme.colors.background)
            .padding(spacingSmall)
    ) {
        Text(
            text = "${cwdn.replaceFirstChar { it.uppercase() }}, $cd",
            color = CustomTheme.colors.secondaryText
        )
    }
}

@Composable
private fun SimpleWeekTypeLabel(
    cwt: Byte = curWeekType(today),
    cwtn: String = curWeekTypeName(today)
) {
    // * Нечетная неделя
    val dotColor =
        if (cwt == 1.toByte()) CustomTheme.colors.oddWeek else CustomTheme.colors.evenWeek
    Row(
        verticalAlignment = Alignment.CenterVertically, modifier = Modifier
            .fillMaxWidth()
            .background(CustomTheme.colors.background)
            .padding(spacingSmall)
    ) {
        Card(
            colors = CardDefaults.cardColors(dotColor),
            modifier = Modifier
                .width(8.dp)
                .height(8.dp)
        ) {}
        Spacer(modifier = Modifier.width(spacingSmall))
        Text(
            text = "${cwtn.replaceFirstChar { it.uppercase() }} неделя",
            color = CustomTheme.colors.secondaryText
        )
    }
}

@Composable
private fun CombinedDateLabel(cwdn: String = curWeekdayName(today)) {
    // Воскресенье, сегодня
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(CustomTheme.colors.background)
            .padding(spacingSmall)
    ) {
        Text(
            text = "${cwdn.replaceFirstChar { it.uppercase() }}, сегодня",
            color = CustomTheme.colors.secondaryText
        )
    }
}

@Composable
private fun CombinedWeekTypeLabel(
    cwt: Byte = curWeekType(today),
    cd: String = curDay(today),
    cwtn: String = curWeekTypeName(today)
) {
    // * 10 сентября, четная неделя
    val dotColor =
        if (cwt == 1.toByte()) CustomTheme.colors.oddWeek else CustomTheme.colors.evenWeek
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .background(CustomTheme.colors.background)
            .padding(spacingSmall)
    ) {
        Card(
            colors = CardDefaults.cardColors(dotColor),
            modifier = Modifier
                .width(8.dp)
                .height(8.dp)
        ) {}
        Spacer(modifier = Modifier.width(spacingSmall))
        Text(
            text = "$cd, $cwtn неделя",
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
            .padding(spacingSmall)
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
private fun LabelsPreview() {
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