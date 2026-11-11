package com.example.ui.components

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.model.GameStatus
import com.example.ui.theme.FredokaFontFamily

@Composable
fun RocketStage(
    wrongGuesses: Int,
    maxWrongGuesses: Int = 6,
    gameStatus: GameStatus,
    modifier: Modifier = Modifier
) {
    val remainingFuel = (maxWrongGuesses - wrongGuesses).coerceIn(0, maxWrongGuesses)

    val infiniteTransition = rememberInfiniteTransition(label = "rocket_thruster")
    val thrustAnim by infiniteTransition.animateFloat(
        initialValue = 0.85f,
        targetValue = 1.25f,
        animationSpec = infiniteRepeatable(
            animation = tween(250, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "thrust"
    )

    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(230.dp)
            .shadow(6.dp, RoundedCornerShape(24.dp)),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF1A237E))
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            Color(0xFF0D1B2A), // Deep outer space
                            Color(0xFF1B263B),
                            Color(0xFF415A77)
                        )
                    )
                )
        ) {
            // Space Stars Canvas
            Canvas(modifier = Modifier.fillMaxSize()) {
                val stars = listOf(
                    Offset(size.width * 0.15f, size.height * 0.2f),
                    Offset(size.width * 0.85f, size.height * 0.25f),
                    Offset(size.width * 0.25f, size.height * 0.75f),
                    Offset(size.width * 0.75f, size.height * 0.8f),
                    Offset(size.width * 0.45f, size.height * 0.15f),
                    Offset(size.width * 0.6f, size.height * 0.6f)
                )
                for (st in stars) {
                    drawCircle(color = Color(0xFFFFD54F), radius = 2.5.dp.toPx(), center = st)
                }

                // Moon
                drawCircle(color = Color(0xFFFFF9C4), radius = 22.dp.toPx(), center = Offset(size.width - 36.dp.toPx(), 40.dp.toPx()))
            }

            // Rocket Graphics
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .offset(y = if (gameStatus == GameStatus.WON) (-30).dp else 0.dp),
                contentAlignment = Alignment.Center
            ) {
                Canvas(modifier = Modifier.size(130.dp, 160.dp)) {
                    val w = size.width
                    val h = size.height

                    // Thruster Fire (when winning or full fuel)
                    if (gameStatus == GameStatus.WON || remainingFuel > 0) {
                        val fireHeight = (28 * thrustAnim).dp.toPx()
                        val firePath = Path().apply {
                            moveTo(w * 0.35f, h * 0.78f)
                            lineTo(w * 0.5f, h * 0.78f + fireHeight)
                            lineTo(w * 0.65f, h * 0.78f)
                            close()
                        }
                        drawPath(firePath, color = Color(0xFFFF7043))
                        val innerFirePath = Path().apply {
                            moveTo(w * 0.42f, h * 0.78f)
                            lineTo(w * 0.5f, h * 0.78f + fireHeight * 0.65f)
                            lineTo(w * 0.58f, h * 0.78f)
                            close()
                        }
                        drawPath(innerFirePath, color = Color(0xFFFFEB3B))
                    }

                    // Rocket Body
                    val bodyPath = Path().apply {
                        moveTo(w * 0.5f, h * 0.12f)
                        cubicTo(w * 0.75f, h * 0.3f, w * 0.72f, h * 0.75f, w * 0.7f, h * 0.78f)
                        lineTo(w * 0.3f, h * 0.78f)
                        cubicTo(w * 0.28f, h * 0.75f, w * 0.25f, h * 0.3f, w * 0.5f, h * 0.12f)
                        close()
                    }
                    drawPath(bodyPath, color = Color(0xFFECEFF1))

                    // Red Fins
                    val leftFin = Path().apply {
                        moveTo(w * 0.3f, h * 0.6f)
                        lineTo(w * 0.12f, h * 0.78f)
                        lineTo(w * 0.3f, h * 0.76f)
                        close()
                    }
                    val rightFin = Path().apply {
                        moveTo(w * 0.7f, h * 0.6f)
                        lineTo(w * 0.88f, h * 0.78f)
                        lineTo(w * 0.7f, h * 0.76f)
                        close()
                    }
                    drawPath(leftFin, color = Color(0xFFE53935))
                    drawPath(rightFin, color = Color(0xFFE53935))

                    // Nose cone
                    val nosePath = Path().apply {
                        moveTo(w * 0.5f, h * 0.12f)
                        lineTo(w * 0.6f, h * 0.32f)
                        lineTo(w * 0.4f, h * 0.32f)
                        close()
                    }
                    drawPath(nosePath, color = Color(0xFFE53935))

                    // Porthole window
                    drawCircle(color = Color(0xFF0288D1), radius = 18.dp.toPx(), center = Offset(w * 0.5f, h * 0.48f))
                    drawCircle(color = Color(0xFFE0E0E0), radius = 18.dp.toPx(), center = Offset(w * 0.5f, h * 0.48f), style = Stroke(width = 3.dp.toPx()))
                }

                // Astronaut Mascot in porthole
                Image(
                    painter = painterResource(id = R.drawable.mascot_bear),
                    contentDescription = "Astronot Ponçik",
                    modifier = Modifier
                        .size(34.dp)
                        .offset(y = (-4).dp)
                        .clip(CircleShape),
                    contentScale = ContentScale.Crop
                )
            }

            // Top Status Pill
            Box(
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(12.dp)
                    .background(Color.White.copy(alpha = 0.9f), RoundedCornerShape(16.dp))
                    .padding(horizontal = 12.dp, vertical = 6.dp)
            ) {
                Text(
                    text = when {
                        gameStatus == GameStatus.WON -> "🚀 Roket Yıldızlara Uçtu! Şampiyon!"
                        gameStatus == GameStatus.LOST -> "🛰️ İniş Kapsülü Açıldı! Tekrar dene!"
                        else -> "⛽ $remainingFuel/6 Yakıt Deposu Dolu!"
                    },
                    fontFamily = FredokaFontFamily,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 13.sp,
                    color = Color(0xFF0D47A1)
                )
            }
        }
    }
}
