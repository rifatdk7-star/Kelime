package com.example.ui.components

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.model.GameStatus
import com.example.ui.theme.BalloonColors
import com.example.ui.theme.FredokaFontFamily
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun BalloonStage(
    wrongGuesses: Int,
    maxWrongGuesses: Int = 6,
    gameStatus: GameStatus,
    modifier: Modifier = Modifier
) {
    val infiniteTransition = rememberInfiniteTransition(label = "float_anim")
    val floatOffset by infiniteTransition.animateFloat(
        initialValue = -6f,
        targetValue = 6f,
        animationSpec = infiniteRepeatable(
            animation = tween(2200, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "floating"
    )

    val parachuteSway by infiniteTransition.animateFloat(
        initialValue = -5f,
        targetValue = 5f,
        animationSpec = infiniteRepeatable(
            animation = tween(1800, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "sway"
    )

    val remainingBalloons = (maxWrongGuesses - wrongGuesses).coerceIn(0, maxWrongGuesses)

    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(230.dp)
            .shadow(6.dp, RoundedCornerShape(24.dp)),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFE1F5FE)
        )
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            Color(0xFF81D4FA), // Sunny sky top
                            Color(0xFFE1F5FE), // Soft cloud bottom
                        )
                    )
                )
        ) {
            // Background Canvas: Clouds & Sun
            Canvas(modifier = Modifier.fillMaxSize()) {
                val w = size.width
                val h = size.height

                // Cheerful bright sun at top-right
                drawCircle(
                    color = Color(0xFFFFEB3B),
                    radius = 28.dp.toPx(),
                    center = Offset(w - 36.dp.toPx(), 36.dp.toPx())
                )
                drawCircle(
                    color = Color(0x40FFF59D),
                    radius = 38.dp.toPx(),
                    center = Offset(w - 36.dp.toPx(), 36.dp.toPx())
                )

                // Gentle puffy clouds at bottom
                drawCircle(
                    color = Color(0xCCFFFFFF),
                    radius = 45.dp.toPx(),
                    center = Offset(40.dp.toPx(), h - 10.dp.toPx())
                )
                drawCircle(
                    color = Color(0xEEFFFFFF),
                    radius = 60.dp.toPx(),
                    center = Offset(110.dp.toPx(), h - 5.dp.toPx())
                )
                drawCircle(
                    color = Color(0xCCFFFFFF),
                    radius = 50.dp.toPx(),
                    center = Offset(w - 50.dp.toPx(), h - 15.dp.toPx())
                )
                drawCircle(
                    color = Color(0xEEFFFFFF),
                    radius = 65.dp.toPx(),
                    center = Offset(w - 120.dp.toPx(), h - 5.dp.toPx())
                )
            }

            // Central Game Scene: Mascot + Balloons OR Parachute
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .offset(y = floatOffset.dp),
                contentAlignment = Alignment.Center
            ) {
                if (gameStatus == GameStatus.LOST || remainingBalloons == 0) {
                    // Safe Parachute Landing Mode! (No death, no violence)
                    SafeParachuteScene(parachuteSway = parachuteSway)
                } else {
                    // Floating with balloons
                    FloatingBalloonsScene(
                        remainingBalloons = remainingBalloons,
                        isWinner = gameStatus == GameStatus.WON
                    )
                }
            }

            // Top Status Pill
            Box(
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(12.dp)
                    .background(Color.White.copy(alpha = 0.88f), RoundedCornerShape(16.dp))
                    .padding(horizontal = 12.dp, vertical = 6.dp)
            ) {
                Text(
                    text = when {
                        gameStatus == GameStatus.WON -> "🎉 Harikasın! Kelimeyi Buldun!"
                        gameStatus == GameStatus.LOST -> "🪂 Ponçik Yumuşak İndi! Tekrar dene!"
                        remainingBalloons == 1 -> "⚠️ Son 1 Balon Kaldı! Dikkat!"
                        else -> "🎈 $remainingBalloons Balon Uçuyor!"
                    },
                    fontFamily = FredokaFontFamily,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 13.sp,
                    color = when {
                        gameStatus == GameStatus.WON -> Color(0xFF2E7D32)
                        gameStatus == GameStatus.LOST -> Color(0xFF0288D1)
                        remainingBalloons == 1 -> Color(0xFFD84315)
                        else -> Color(0xFF01579B)
                    }
                )
            }
        }
    }
}

@Composable
private fun FloatingBalloonsScene(
    remainingBalloons: Int,
    isWinner: Boolean
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp),
        contentAlignment = Alignment.Center
    ) {
        // Balloon Strings and Balloons via Canvas
        Canvas(
            modifier = Modifier
                .fillMaxSize()
        ) {
            val centerX = size.width / 2f
            val handY = size.height * 0.65f // Mascot holding position

            // Balloon relative positions (6 slots in fan arc)
            val balloonAngles = listOf(-55f, -33f, -11f, 11f, 33f, 55f)
            val stringLength = 80.dp.toPx()

            for (i in 0 until 6) {
                val isAvailable = i < remainingBalloons
                val angleDeg = balloonAngles[i]
                val angleRad = Math.toRadians((angleDeg - 90).toDouble())

                val balloonX = centerX + (cos(angleRad) * stringLength).toFloat()
                val balloonY = handY + (sin(angleRad) * stringLength).toFloat()

                if (isAvailable) {
                    val color = BalloonColors[i % BalloonColors.size]

                    // Draw string to hand
                    val stringPath = Path().apply {
                        moveTo(handY, centerX) // dummy
                        moveTo(centerX, handY)
                        quadraticTo(
                            (centerX + balloonX) / 2f + (if (i % 2 == 0) -8f else 8f),
                            (handY + balloonY) / 2f,
                            balloonX,
                            balloonY + 18.dp.toPx()
                        )
                    }
                    drawPath(
                        path = stringPath,
                        color = Color(0x99546E7A),
                        style = Stroke(width = 2.dp.toPx())
                    )

                    // Draw Balloon Oval
                    val balloonWidth = 32.dp.toPx()
                    val balloonHeight = 40.dp.toPx()
                    drawOval(
                        color = color,
                        topLeft = Offset(balloonX - balloonWidth / 2f, balloonY - balloonHeight / 2f),
                        size = Size(balloonWidth, balloonHeight),
                        style = Fill
                    )

                    // Draw Balloon Knot
                    val knotPath = Path().apply {
                        moveTo(balloonX - 3.dp.toPx(), balloonY + balloonHeight / 2f)
                        lineTo(balloonX + 3.dp.toPx(), balloonY + balloonHeight / 2f)
                        lineTo(balloonX, balloonY + balloonHeight / 2f + 4.dp.toPx())
                        close()
                    }
                    drawPath(path = knotPath, color = color, style = Fill)

                    // Balloon 3D Shine highlight
                    drawCircle(
                        color = Color.White.copy(alpha = 0.55f),
                        radius = 4.5.dp.toPx(),
                        center = Offset(balloonX - 6.dp.toPx(), balloonY - 8.dp.toPx())
                    )
                } else {
                    // Popped balloon: Draw mini colorful burst stars
                    val burstColor = BalloonColors[i % BalloonColors.size].copy(alpha = 0.45f)
                    for (b in 0 until 4) {
                        val popAngle = (b * 90) * Math.PI / 180.0
                        val px = balloonX + (cos(popAngle) * 10.dp.toPx()).toFloat()
                        val py = balloonY + (sin(popAngle) * 10.dp.toPx()).toFloat()
                        drawCircle(color = burstColor, radius = 2.dp.toPx(), center = Offset(px, py))
                    }
                }
            }
        }

        // Mascot Bear Image at Center Bottom
        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .offset(y = (-14).dp)
                .size(92.dp),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.mascot_bear),
                contentDescription = "Ponçik",
                modifier = Modifier
                    .size(86.dp)
                    .clip(CircleShape)
                    .shadow(4.dp, CircleShape),
                contentScale = ContentScale.Crop
            )

            // Winner Golden Crown!
            if (isWinner) {
                Text(
                    text = "👑",
                    fontSize = 28.sp,
                    modifier = Modifier
                        .align(Alignment.TopCenter)
                        .offset(y = (-18).dp)
                )
            }
        }
    }
}

@Composable
private fun SafeParachuteScene(parachuteSway: Float) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp),
        contentAlignment = Alignment.Center
    ) {
        // Draw Rainbow Parachute Canopy and safety ropes
        Canvas(
            modifier = Modifier
                .fillMaxSize()
                .offset(x = parachuteSway.dp)
        ) {
            val centerX = size.width / 2f
            val topY = 28.dp.toPx()
            val canopyWidth = 140.dp.toPx()
            val canopyHeight = 55.dp.toPx()
            val basketY = 125.dp.toPx()

            // 4 Safety Ropes
            val ropeColor = Color(0xFF37474F)
            drawLine(ropeColor, Offset(centerX - canopyWidth * 0.45f, topY + canopyHeight), Offset(centerX, basketY), strokeWidth = 2.dp.toPx())
            drawLine(ropeColor, Offset(centerX - canopyWidth * 0.15f, topY + canopyHeight), Offset(centerX, basketY), strokeWidth = 2.dp.toPx())
            drawLine(ropeColor, Offset(centerX + canopyWidth * 0.15f, topY + canopyHeight), Offset(centerX, basketY), strokeWidth = 2.dp.toPx())
            drawLine(ropeColor, Offset(centerX + canopyWidth * 0.45f, topY + canopyHeight), Offset(centerX, basketY), strokeWidth = 2.dp.toPx())

            // Parachute Colorful Segments
            val colors = listOf(Color(0xFFFF5252), Color(0xFFFFD600), Color(0xFF4CAF50), Color(0xFF29B6F6), Color(0xFFAB47BC))
            val segmentWidth = canopyWidth / colors.size

            for (idx in colors.indices) {
                val segLeft = centerX - (canopyWidth / 2f) + (idx * segmentWidth)
                val path = Path().apply {
                    moveTo(segLeft, topY + canopyHeight)
                    quadraticTo(
                        segLeft + segmentWidth / 2f,
                        topY,
                        segLeft + segmentWidth,
                        topY + canopyHeight
                    )
                    close()
                }
                drawPath(path = path, color = colors[idx], style = Fill)
            }

            // Fluffy Landing Trampoline on the ground
            drawRoundRect(
                color = Color(0xFFFF7043),
                topLeft = Offset(centerX - 60.dp.toPx(), size.height - 24.dp.toPx()),
                size = Size(120.dp.toPx(), 16.dp.toPx()),
                cornerRadius = androidx.compose.ui.geometry.CornerRadius(8.dp.toPx(), 8.dp.toPx())
            )
        }

        // Mascot Bear descending peacefully with parachute
        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .offset(x = parachuteSway.dp, y = (-24).dp)
                .size(76.dp),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.mascot_bear),
                contentDescription = "Ponçik Paraşütle Güvende",
                modifier = Modifier
                    .size(70.dp)
                    .clip(CircleShape)
                    .shadow(3.dp, CircleShape),
                contentScale = ContentScale.Crop
            )
        }
    }
}
