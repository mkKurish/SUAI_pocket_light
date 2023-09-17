package com.example.suai_pocket_light

import android.content.res.Configuration
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.suai_pocket_light.ui.theme.CustomTheme
import com.example.suai_pocket_light.ui.theme.MainTheme
import com.example.suai_pocket_light.ui.theme.spacingMedium
import com.example.suai_pocket_light.ui.theme.spacingSmall

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GroupLabel(content: String){
    Card(
        colors = CardDefaults.cardColors(CustomTheme.colors.mainCard),
        onClick = {
            TODO("List of groups to choose from")
        }
    ) {
        Text(text = content, color = CustomTheme.colors.primaryText,
            modifier = Modifier.padding(spacingMedium))
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES, name = "Dark mode")
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO, name = "Light mode")
@Composable
private fun GroupLabelPreview(){
    MainTheme {
        GroupLabel(content = "4232")
    }
}