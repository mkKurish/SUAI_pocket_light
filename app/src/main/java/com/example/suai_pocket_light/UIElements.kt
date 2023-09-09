package com.example.suai_pocket_light

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.suai_pocket_light.PariTypes.Companion.getTypeColor
import com.example.suai_pocket_light.ui.theme.CustomTheme
import com.example.suai_pocket_light.ui.theme.MainTheme
import com.example.suai_pocket_light.ui.theme.cornersRadius
import com.example.suai_pocket_light.ui.theme.spacingLarge
import com.example.suai_pocket_light.ui.theme.spacingMedium
import com.example.suai_pocket_light.ui.theme.spacingSmall

@Composable
fun SubjectsContent(subjects: List<Subject>) {
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
                        modifier = Modifier
                            .width(3.dp)
                            .fillMaxHeight()
                            .padding(vertical = spacingMedium),
                        shape = RoundedCornerShape(50.dp)
                    ) {
                        Box(
                            Modifier
                                .fillMaxSize()
                                .background(CustomTheme.colors.secondaryText)
                        )
                    }
                    Spacer(modifier = Modifier.width(spacingMedium))
                    Column(
                        verticalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxHeight()
                    ) {
                        Text(text = it.type.paraType, color = getTypeColor(it.type))
                        Text(text = it.discidpline, color = CustomTheme.colors.primaryText, fontWeight = FontWeight.SemiBold)
                        Row(){
                            Card (shape = RoundedCornerShape(cornersRadius)){
                                Box(modifier = Modifier.background(CustomTheme.colors.additionalCard)){
                                    Text(modifier = Modifier.padding(horizontal = spacingSmall), text = it.room, fontSize = 10.sp, color = CustomTheme.colors.secondaryText)
                                }
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
fun Subjects(
    subjects: List<Subject>
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp * subjects.size + spacingLarge * 2 + spacingMedium * (subjects.size - 1))
            .padding(horizontal = spacingMedium, vertical = spacingSmall),
        shape = RoundedCornerShape(cornersRadius)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(CustomTheme.colors.mainCard)
        ) {
            SubjectsContent(subjects)
        }
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES, name = "Dark mode")
@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO, name = "Light mode")
@Composable
fun PreviewSubjects() {
    MainTheme() {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(400.dp)
        ) {
            Column {
                Subjects(
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
                    )
                )
                Subjects(
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
            }
        }
    }
}