package com.example.suai_pocket_light

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.times
import com.example.suai_pocket_light.ui.theme.CustomTheme

@Composable
fun SUAILogo(logoSize: Dp, fknColor: Color) {
    val fAngleState = rememberInfiniteTransition(label = "")
    val sAngleState = rememberInfiniteTransition(label = "")
    val fAngle by fAngleState.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 2000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ), label = ""
    )
    val sAngle by sAngleState.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 4000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ), label = ""
    )
    Box(contentAlignment = Alignment.Center) {
        Box(modifier = Modifier
            .size(logoSize)
            .padding(0.05f * logoSize)
            .drawBehind() {
                drawArc(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color(0xFFdf0541),
                            Color(0xFF0059a9)
                        )
                    ),
                    startAngle = fAngle,
                    sweepAngle = sAngle,
                    useCenter = false,
                    size = Size(size.width / 4, size.height),
                    style = Stroke(0.05f * logoSize.toPx()),
                    topLeft = Offset(3 * size.width / 8, 0f)
                )
                drawArc(
                    color = fknColor,
                    startAngle = 270f,
                    sweepAngle = 180f,
                    useCenter = false,
                    size = Size(size.width, size.height / 4),
                    style = Fill,
                    topLeft = Offset(0f, 3 * size.height / 8)
                )
                drawArc(
                    brush = Brush.horizontalGradient(
                        colors = listOf(
                            Color(0xFF01b4ec),
                            Color(0xFF0059a9)
                        )
                    ),
                    startAngle = fAngle,
                    sweepAngle = sAngle,
                    useCenter = false,
                    size = Size(size.width, size.height / 4),
                    style = Stroke(0.05f * logoSize.toPx()),
                    topLeft = Offset(0f, 3 * size.height / 8)
                )
                drawArc(
                    color = fknColor,
                    startAngle = 180f,
                    sweepAngle = 180f,
                    useCenter = false,
                    size = Size(size.width * 0.18f, size.height * 0.8f),
                    style = Fill,
                    topLeft = Offset(size.width * 0.41f, size.height * 0.02f)
                )
            })
    }
}