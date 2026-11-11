package com.example.ui.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.spring
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.GameStatus
import com.example.ui.theme.FredokaFontFamily

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun WordDisplay(
    word: String,
    guessedLetters: Set<Char>,
    gameStatus: GameStatus,
    modifier: Modifier = Modifier
) {
    val isRevealedEnd = gameStatus == GameStatus.LOST

    // Dynamic tile dimensions based on word length
    val tileWidth = when {
        word.length <= 4 -> 48.dp
        word.length <= 6 -> 42.dp
        word.length <= 8 -> 36.dp
        else -> 32.dp
    }
    val tileHeight = when {
        word.length <= 4 -> 56.dp
        word.length <= 6 -> 50.dp
        word.length <= 8 -> 44.dp
        else -> 40.dp
    }
    val fontSize = when {
        word.length <= 4 -> 28.sp
        word.length <= 6 -> 24.sp
        word.length <= 8 -> 20.sp
        else -> 18.sp
    }

    FlowRow(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 6.dp),
        horizontalArrangement = Arrangement.Center,
        verticalArrangement = Arrangement.Center
    ) {
        word.forEachIndexed { index, char ->
            if (char == ' ') {
                Box(modifier = Modifier.width(16.dp))
            } else {
                val isGuessed = guessedLetters.contains(char)
                val showChar = isGuessed || isRevealedEnd
                val missedAtEnd = isRevealedEnd && !isGuessed

                Box(
                    modifier = Modifier
                        .padding(horizontal = 3.dp, vertical = 4.dp)
                        .size(width = tileWidth, height = tileHeight)
                        .shadow(
                            elevation = if (showChar) 4.dp else 2.dp,
                            shape = RoundedCornerShape(12.dp)
                        )
                        .clip(RoundedCornerShape(12.dp))
                        .background(
                            when {
                                missedAtEnd -> Color(0xFFFFEBEE) // Soft pink for missed letters
                                isGuessed -> Color(0xFFE8F5E9)   // Mint green for guessed
                                else -> Color(0xFFFFFFFF)       // Crisp white blank tile
                            }
                        )
                        .border(
                            width = 2.dp,
                            color = when {
                                missedAtEnd -> Color(0xFFEF5350)
                                isGuessed -> Color(0xFF66BB6A)
                                else -> Color(0xFF90CAF9)
                            },
                            shape = RoundedCornerShape(12.dp)
                        )
                        .testTag("letter_tile_$index"),
                    contentAlignment = Alignment.Center
                ) {
                    AnimatedContent(
                        targetState = showChar,
                        transitionSpec = {
                            fadeIn(spring()) togetherWith fadeOut(spring())
                        },
                        label = "char_anim"
                    ) { visible ->
                        if (visible) {
                            Text(
                                text = char.toString(),
                                fontFamily = FredokaFontFamily,
                                fontWeight = FontWeight.Bold,
                                fontSize = fontSize,
                                textAlign = TextAlign.Center,
                                color = when {
                                    missedAtEnd -> Color(0xFFC62828)
                                    isGuessed -> Color(0xFF1B5E20)
                                    else -> Color(0xFF0D47A1)
                                }
                            )
                        } else {
                            // Underline indicator
                            Box(
                                modifier = Modifier
                                    .size(width = 14.dp, height = 4.dp)
                                    .background(Color(0xFF90CAF9), RoundedCornerShape(2.dp))
                            )
                        }
                    }
                }
            }
        }
    }
}
