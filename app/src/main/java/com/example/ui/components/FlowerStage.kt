package com.example.ui.components

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
import com.example.ui.theme.BalloonColors
import com.example.ui.theme.FredokaFontFamily
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun FlowerStage(
    wrongGuesses: Int,
    maxWrongGuesses: Int = 6,
    gameStatus: GameStatus,
    modifier: Modifier = Modifier
) {
    val remainingPetals = (maxWrongGuesses - wrongGuesses).coerceIn(0, maxWrongGuesses)

    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(230.dp)
            .shadow(6.dp, RoundedCornerShape(24.dp)),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFE8F5E9))
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            Color(0xFFFFF9C4), // Warm morning sunshine
                            Color(0xFFE8F5E9), // Lush green garden
                        )
                    )
                )
        ) {
            // Draw Flower Petals & Stem via Canvas
            Canvas(modifier = Modifier.fillMaxSize()) {
                val centerX = size.width / 2f
                val flowerY = size.height * 0.48f

                // Green Stem
                val stemPath = Path().apply {
                    moveTo(centerX, flowerY)
                    quadraticTo(centerX + 15.dp.toPx(), flowerY + 45.dp.toPx(), centerX, size.height - 20.dp.toPx())
                }
                drawPath(stemPath, color = Color(0xFF43A047), style = Stroke(width = 8.dp.toPx()))

                // Green Leaves
                val leafLeft = Path().apply {
                    moveTo(centerX + 6.dp.toPx(), flowerY + 45.dp.toPx())
                    quadraticTo(centerX - 35.dp.toPx(), flowerY + 30.dp.toPx(), centerX - 40.dp.toPx(), flowerY + 45.dp.toPx())
                    quadraticTo(centerX - 20.dp.toPx(), flowerY + 55.dp.toPx(), centerX + 6.dp.toPx(), flowerY + 45.dp.toPx())
                    close()
                }
                drawPath(leafLeft, color = Color(0xFF4CAF50), style = Fill)

                // 6 Petals around center
                val petalRadius = 46.dp.toPx()
                for (i in 0 until 6) {
                    val isAlive = i < remainingPetals
                    val angle = (i * 60.0) * Math.PI / 180.0
                    val px = centerX + (cos(angle) * petalRadius).toFloat()
                    val py = flowerY + (sin(angle) * petalRadius).toFloat()

                    if (isAlive) {
                        val petalColor = BalloonColors[i % BalloonColors.size]
                        drawCircle(color = petalColor, radius = 22.dp.toPx(), center = Offset(px, py))
                        drawCircle(color = Color.White.copy(alpha = 0.4f), radius = 6.dp.toPx(), center = Offset(px - 5.dp.toPx(), py - 5.dp.toPx()))
                    } else {
                        // Fallen petal on the garden floor
                        val groundX = centerX + (if (i % 2 == 0) -1 else 1) * (40.dp.toPx() + i * 15.dp.toPx())
                        drawOval(
                            color = BalloonColors[i % BalloonColors.size].copy(alpha = 0.6f),
                            topLeft = Offset(groundX, size.height - 22.dp.toPx()),
                            size = Size(18.dp.toPx(), 8.dp.toPx())
                        )
                    }
                }

                // Golden Center of Flower
                drawCircle(color = Color(0xFFFFB300), radius = 28.dp.toPx(), center = Offset(centerX, flowerY))
            }

            // Smiling Mascot inside flower center
            Box(
                modifier = Modifier
                    .align(Alignment.Center)
                    .offset(y = (-4).dp)
                    .size(46.dp),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.mascot_bear),
                    contentDescription = "Çiçek Ponçik",
                    modifier = Modifier
                        .size(42.dp)
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
                        gameStatus == GameStatus.WON -> "🌸 Çiçeğimiz Rengarenk Açtı!"
                        gameStatus == GameStatus.LOST -> "🌱 Çiçek Sulandı, Yarın Açacak!"
                        else -> "🌸 $remainingPetals/6 Renkli Yaprak Kaldı!"
                    },
                    fontFamily = FredokaFontFamily,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 13.sp,
                    color = Color(0xFF2E7D32)
                )
            }
        }
    }
}
