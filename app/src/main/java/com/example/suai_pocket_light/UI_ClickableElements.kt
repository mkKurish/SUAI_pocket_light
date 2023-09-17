package com.example.suai_pocket_light

import android.content.Context
import android.content.res.Configuration
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.suai_pocket_light.DataParser.setPrefGroup
import com.example.suai_pocket_light.ui.theme.CustomTheme
import com.example.suai_pocket_light.ui.theme.MainTheme
import com.example.suai_pocket_light.ui.theme.cornersRadius
import com.example.suai_pocket_light.ui.theme.spacingLarge
import com.example.suai_pocket_light.ui.theme.spacingMedium
import com.example.suai_pocket_light.ui.theme.spacingSmall
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GroupLabel(content: String) {
    Card(
        colors = CardDefaults.cardColors(CustomTheme.colors.mainCard),
        onClick = {
            showFilter.value = true
        }
    ) {
        Row(
            modifier = Modifier.padding(start = spacingMedium),
            verticalAlignment = Alignment.CenterVertically
        ) {
            SUAILogo(logoSize = 25.dp, CustomTheme.colors.mainCard)
            Text(
                text = content, color = CustomTheme.colors.primaryText,
                modifier = Modifier.padding(spacingMedium)
            )
        }
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES, name = "Dark mode")
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO, name = "Light mode")
@Composable
private fun GroupLabelPreview() {
    MainTheme {
        GroupLabel(content = "4232")
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SelectingGroupDialog(appContext: Context) {
    val groups = DataParser.getGroups(appContext)
    val interactionSource = remember { MutableInteractionSource() }
    var userGroupState = remember { mutableStateOf("") }
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
                        ), textStyle = TextStyle(fontSize = 20.sp)
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
        requiredGroup = content
        runBlocking { launch { setPrefGroup(appContext) } }
        subjectsList = DataParser.parseSubjects(appContext)
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