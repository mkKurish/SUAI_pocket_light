package com.example.suai_pocket_light

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.suai_pocket_light.ui.theme.CustomTheme
import com.example.suai_pocket_light.ui.theme.spacingMedium

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