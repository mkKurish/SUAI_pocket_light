package com.example.suai_pocket_light

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import com.example.suai_pocket_light.ui.theme.CustomTheme
import com.example.suai_pocket_light.ui.theme.MainTheme
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        val subjectsList = DataParser.parseSubjects(this)

        super.onCreate(savedInstanceState)
        setContent {
            MainTheme {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(color = CustomTheme.colors.background)
                ){
                    ListOfSubjects(subjectsList)
                }
            }
        }
    }
}