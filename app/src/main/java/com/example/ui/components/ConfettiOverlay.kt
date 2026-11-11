package com.example.ui.components

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.rotate
import kotlin.random.Random

private data class ConfettiParticle(
    val xRatio: Float,
    val speed: Float,
    val size: Float,
    val color: Color,
    val rotationSpeed: Float,
    val initialAngle: Float
)

@Composable
fun ConfettiOverlay(modifier: Modifier = Modifier) {
    val progress = remember { Animatable(0f) }

    val confettiList = remember {
        val colors = listOf(
            Color(0xFFFF5252),
            Color(0xFFFFD600),
            Color(0xFF4CAF50),
            Color(0xFF29B6F6),
            Color(0xFFAB47BC),
            Color(0xFFFF4081)
        )
        List(45) {
            ConfettiParticle(
                xRatio = Random.nextFloat(),
                speed = Random.nextFloat() * 0.7f + 0.6f,
                size = Random.nextFloat() * 10f + 8f,
                color = colors[Random.nextInt(colors.size)],
                rotationSpeed = (Random.nextFloat() * 720f) - 360f,
                initialAngle = Random.nextFloat() * 360f
            )
        }
    }

    LaunchedEffect(Unit) {
        progress.animateTo(
            targetValue = 1f,
            animationSpec = tween(durationMillis = 2800, easing = LinearEasing)
        )
    }

    Canvas(modifier = modifier.fillMaxSize()) {
        val canvasWidth = size.width
        val canvasHeight = size.height

        confettiList.forEach { p ->
            val y = (p.speed * progress.value * canvasHeight * 1.2f) - 20f
            val x = p.xRatio * canvasWidth + (sin(y / 30f) * 15f)
            val currentRotation = p.initialAngle + (p.rotationSpeed * progress.value)

            rotate(degrees = currentRotation, pivot = Offset(x, y)) {
                drawRect(
                    color = p.color,
                    topLeft = Offset(x - p.size / 2f, y - p.size / 2f),
                    size = Size(p.size, p.size * 1.5f)
                )
            }
        }
    }
}

private fun sin(x: Float): Float = kotlin.math.sin(x.toDouble()).toFloat()
