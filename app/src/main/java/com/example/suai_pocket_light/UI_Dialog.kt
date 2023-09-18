package com.example.suai_pocket_light

import android.content.Context
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.suai_pocket_light.structures.Group
import com.example.suai_pocket_light.utils.internetActions.DataParser
import com.example.suai_pocket_light.utils.internetActions.checkInternet
import com.example.suai_pocket_light.ui.theme.CustomTheme
import com.example.suai_pocket_light.ui.theme.cornersRadius
import com.example.suai_pocket_light.ui.theme.spacingLarge
import com.example.suai_pocket_light.ui.theme.spacingMedium
import com.example.suai_pocket_light.ui.theme.spacingSmall
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SelectingGroupDialog(appContext: Context) {
    val interactionSource = remember { MutableInteractionSource() }
    val userGroupState = remember { mutableStateOf("") }
    if (groups.isEmpty() && checkInternet(appContext))
        groups = DataParser.getGroups(appContext)
    Box(
        modifier = Modifier
            .fillMaxSize()
            .clickable(interactionSource = interactionSource, indication = null) {
                showFilter.value = false
            }, contentAlignment = Alignment.Center
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .fillMaxHeight(0.6f),
            colors = CardDefaults.cardColors(CustomTheme.colors.background),
            shape = RoundedCornerShape(cornersRadius),
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(spacingLarge).clickable(interactionSource = interactionSource, indication = null) {}
            ) {
                Column {
                    TextField(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(spacingSmall), label = { Text(text = "группа №") },
                        value = userGroupState.value, onValueChange = { userGroupState.value = it },
                        colors = TextFieldDefaults.textFieldColors(
                            textColor = CustomTheme.colors.primaryText,
                            disabledTextColor = CustomTheme.colors.primaryText,
                            cursorColor = CustomTheme.colors.primaryText,
                            focusedIndicatorColor = CustomTheme.colors.ghostElement,
                            unfocusedIndicatorColor = CustomTheme.colors.ghostElement,
                            containerColor = CustomTheme.colors.background,
                            focusedLabelColor = CustomTheme.colors.primaryText,
                            unfocusedLabelColor = CustomTheme.colors.secondaryText
                        ), textStyle = TextStyle(fontSize = 20.sp), maxLines = 1
                    )
                    LazyColumn {
                        groups.filter { it.Name.matches("""${userGroupState.value.uppercase()}.*""".toRegex()) }
                            .forEach {
                                item {
                                    GroupDialogItem(it, appContext)
                                }
                            }
                    }
                }
            }
        }
    }
    BackHandler {
        showFilter.value = false
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun GroupDialogItem(content: Group, appContext: Context) {
    Card(onClick = {
        if (checkInternet(appContext)){
            requiredGroup = content
            runBlocking { launch { DataParser.setPrefGroup(appContext) } }
            subjectsList = DataParser.parseSubjects(appContext)
        }else Toast.makeText(appContext, "Интернет-соединение пропало", Toast.LENGTH_SHORT).show()
        showFilter.value = false
    }, colors = CardDefaults.cardColors(CustomTheme.colors.mainCard)) {
        Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
            Text(
                modifier = Modifier
                    .padding(vertical = spacingMedium),
                text = content.Name,
                color = CustomTheme.colors.primaryText,
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}