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

val spacingSmall: Dp = 5.dp
val spacingMedium: Dp = 10.dp
val spacingLarge: Dp = 15.dp

val cornersRadius: Dp = 10.dp

data class CustomColors(
    val primaryText: Color,
    val secondaryText: Color,
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
    labText = Color(0xFF17B2E5),
    praktText = Color(0xFFE5780B),
    lekcText = Color(0xFF9130F2),
    kursText = Color(0xFF21991F),
    oddWeek = Color(0xFFE52E5A),
    evenWeek = Color(0xFF322EE5),
    background = Color(0xFF222222),
    mainCard = Color(0xFF2A2A2A),
    additionalCard = Color(0xFF404040)
)

fun customLightColors() = CustomColors(
    primaryText = Color(0xFF000000),
    secondaryText = Color(0xFF888888),
    labText = Color(0xFF0099CC),
    praktText = Color(0xFFE57300),
    lekcText = Color(0xFF8000FF),
    kursText = Color(0xFF04B200),
    oddWeek = Color(0xFFB9002C),
    evenWeek = Color(0xFF0400B9),
    background = Color(0xFFF2F2F2),
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