package com.example.suai_pocket_light.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.suai_pocket_light.R
import com.google.accompanist.systemuicontroller.rememberSystemUiController

val spacingSmall: Dp = 4.dp
val spacingMedium: Dp = 10.dp
val spacingLarge: Dp = 20.dp

val cornersRadius: Dp = 15.dp

data class CustomColors(
    val primaryText: Color,
    val secondaryText: Color,
    val ghostElement: Color,
    val labText: Color,
    val praktText: Color,
    val lekcText: Color,
    val kursText: Color,
    val oddWeek: Color,
    val evenWeek: Color,
    val background: Color,
    val mainCard: Color,
    val additionalCard: Color
)

fun customDarkColors() = CustomColors(
    primaryText = Color(0xFFFFFFFF),
    secondaryText = Color(0xFF999999),
    ghostElement = Color(0xFF595959),
    labText = Color(0xFF00BCD4),
    praktText = Color(0xFFFF9800),
    lekcText = Color(0xFF9C27B0),
    kursText = Color(0xFF4CAF50),
    oddWeek = Color(0xFFF44336),
    evenWeek = Color(0xFF2196F3),
    background = Color(0xFF0A0A0A),
    mainCard = Color(0xFF191919),
    additionalCard = Color(0xFF292929)
)

fun customLightColors() = CustomColors(
    primaryText = Color(0xFF000000),
    secondaryText = Color(0xFF888888),
    ghostElement = Color(0xFFCCCCCC),
    labText = Color(0xFF039BE5),
    praktText = Color(0xFFFB8C00),
    lekcText = Color(0xFF8E24AA),
    kursText = Color(0xFF43A047),
    oddWeek = Color(0xFFE53935),
    evenWeek = Color(0xFF1E88E5),
    background = Color(0xFFECEDF0),
    mainCard = Color(0xFFFFFFFF),
    additionalCard = Color(0xFFEEEEEE)
)

data class CustomImages(
    val calendar_pic: Int
)

fun customDarkImages() = CustomImages(
    calendar_pic = R.drawable.calendar_icon_dark
)
fun customLightImages() = CustomImages(
    calendar_pic = R.drawable.calendar_icon_light
)

object CustomTheme {
    val colors: CustomColors
        @Composable
        get() = localCustomColors.current
    val images: CustomImages
        @Composable
        get() = localCustomImages.current
}

val localCustomColors = staticCompositionLocalOf<CustomColors> {
    error("No colors provided")
}

val localCustomImages = staticCompositionLocalOf<CustomImages> {
    error("No images provided")
}

@Composable
fun MainTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    val systemUiController = rememberSystemUiController()
    val colors = if (isSystemInDarkTheme()) customDarkColors() else customLightColors()
    val images = if (isSystemInDarkTheme()) customDarkImages() else customLightImages()
    systemUiController.setSystemBarsColor(
        color = colors.background,
        darkIcons = !darkTheme
    )
    CompositionLocalProvider(
        localCustomColors provides colors,
        localCustomImages provides images,
        content = content
    )
}