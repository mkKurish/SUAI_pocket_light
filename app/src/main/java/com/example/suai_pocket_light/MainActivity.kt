package com.example.suai_pocket_light

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import com.example.suai_pocket_light.ui.theme.CustomTheme
import com.example.suai_pocket_light.ui.theme.MainTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        val subjectsList = DataParser.parseSubjects()
        super.onCreate(savedInstanceState)
        setContent {
            MainTheme {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(color = CustomTheme.colors.background)
                ){
                    Column(modifier = Modifier.verticalScroll(ScrollState(0))){
                        subjectsList.forEach {
                            Box(modifier = Modifier.fillMaxWidth()){
                                Text(text = "Сегодня ${it[0].day}; Неделя: ${it[0].week}", color = CustomTheme.colors.secondaryText)
                            }
                            Subjects(it)
                        }
                    }
                }
            }
        }
    }
}