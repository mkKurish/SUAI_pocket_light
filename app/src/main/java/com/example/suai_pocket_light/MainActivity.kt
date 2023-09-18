package com.example.suai_pocket_light

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.unit.dp
import com.example.suai_pocket_light.structures.Group
import com.example.suai_pocket_light.structures.Subject
import com.example.suai_pocket_light.ui.theme.CustomTheme
import com.example.suai_pocket_light.ui.theme.MainTheme
import com.example.suai_pocket_light.utils.internetActions.DataParser
import com.example.suai_pocket_light.utils.internetActions.DataParser.usePrefGroup
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

val showFilter = mutableStateOf(false)
var requiredGroup: Group = Group("unll", 0)
var subjectsList: List<List<Subject>> = listOf()
var groups: List<Group> = listOf()

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalAnimationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {

        groups = DataParser.getGroups(this)
        runBlocking { launch { usePrefGroup(this@MainActivity) } }

        super.onCreate(savedInstanceState)
        setContent {
            MainTheme {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(color = CustomTheme.colors.background)
                        .blur(if (showFilter.value) 5.dp else 0.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    GroupLabel(content = requiredGroup.Name)
                    ListOfSubjects(subjectsList)
                }
                AnimatedVisibility(
                    visible = showFilter.value,
                    enter = scaleIn() + fadeIn(),
                    exit = scaleOut() + fadeOut()
                ) {
                    SelectingGroupDialog(this@MainActivity)
                }
            }
        }
    }
}
