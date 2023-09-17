package com.example.suai_pocket_light

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.suai_pocket_light.ui.theme.CustomTheme

@Composable
fun button(content: String){
    Card(
        colors = CardDefaults.cardColors(CustomTheme.colors.mainCard),
        modifier = Modifier
            .width(8.dp)
            .height(8.dp)
    ) {}
}