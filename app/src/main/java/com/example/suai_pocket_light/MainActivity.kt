package com.example.suai_pocket_light

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
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
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(color = CustomTheme.colors.background)
                ) {
                    Row(horizontalArrangement = Arrangement.SpaceBetween){
                        SUAILogo(25.dp)

                    }
                    ListOfSubjects(subjectsList)
                }
//                Box(
//                    modifier = Modifier
//                        .fillMaxSize()
//                        .background(color = CustomTheme.colors.background)
//                ){
//                    ListOfSubjects(subjectsList)
//                }
            }
        }
    }
}