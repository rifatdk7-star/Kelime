package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.FredokaFontFamily

private val TURKISH_VOWELS = setOf('A', 'E', 'I', 'İ', 'O', 'Ö', 'U', 'Ü')

@Composable
fun TurkishKeyboard(
    currentWord: String,
    guessedLetters: Set<Char>,
    highlightVowels: Boolean,
    enabled: Boolean,
    onLetterClick: (Char) -> Unit,
    modifier: Modifier = Modifier
) {
    val row1 = listOf('E', 'R', 'T', 'Y', 'U', 'I', 'O', 'P', 'Ğ', 'Ü')
    val row2 = listOf('A', 'S', 'D', 'F', 'G', 'H', 'J', 'K', 'L', 'Ş')
    val row3 = listOf('Z', 'C', 'Ç', 'V', 'B', 'N', 'M', 'Ö', 'İ')

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 4.dp, vertical = 2.dp),
        verticalArrangement = Arrangement.spacedBy(6.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        KeyboardRow(
            letters = row1,
            currentWord = currentWord,
            guessedLetters = guessedLetters,
            highlightVowels = highlightVowels,
            enabled = enabled,
            onLetterClick = onLetterClick
        )
        KeyboardRow(
            letters = row2,
            currentWord = currentWord,
            guessedLetters = guessedLetters,
            highlightVowels = highlightVowels,
            enabled = enabled,
            onLetterClick = onLetterClick
        )
        KeyboardRow(
            letters = row3,
            currentWord = currentWord,
            guessedLetters = guessedLetters,
            highlightVowels = highlightVowels,
            enabled = enabled,
            onLetterClick = onLetterClick
        )
    }
}

@Composable
private fun KeyboardRow(
    letters: List<Char>,
    currentWord: String,
    guessedLetters: Set<Char>,
    highlightVowels: Boolean,
    enabled: Boolean,
    onLetterClick: (Char) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(3.5.dp, Alignment.CenterHorizontally),
        verticalAlignment = Alignment.CenterVertically
    ) {
        letters.forEach { char ->
            val isGuessed = guessedLetters.contains(char)
            val isInWord = isGuessed && currentWord.contains(char)
            val isVowel = TURKISH_VOWELS.contains(char)

            val backgroundColor = when {
                isGuessed && isInWord -> Color(0xFFC8E6C9) // Pastel green for correct
                isGuessed -> Color(0xFFECEFF1)            // Soft grey for wrong
                highlightVowels && isVowel -> Color(0xFFFFF9C4) // Sunny yellow highlight for vowels
                else -> Color(0xFFFFFFFF)                 // White active key
            }

            val textColor = when {
                isGuessed && isInWord -> Color(0xFF2E7D32)
                isGuessed -> Color(0xFFB0BEC5)
                highlightVowels && isVowel -> Color(0xFFF57F17)
                else -> Color(0xFF1E88E5)
            }

            val borderColor = when {
                isGuessed && isInWord -> Color(0xFF81C784)
                isGuessed -> Color(0xFFCFD8DC)
                highlightVowels && isVowel -> Color(0xFFFFB300)
                else -> Color(0xFFBBDEFB)
            }

            val isKeyClickable = enabled && !isGuessed

            Box(
                modifier = Modifier
                    .weight(1f)
                    .height(48.dp)
                    .shadow(
                        elevation = if (isKeyClickable) 3.dp else 0.dp,
                        shape = RoundedCornerShape(10.dp)
                    )
                    .clip(RoundedCornerShape(10.dp))
                    .background(backgroundColor)
                    .border(width = 1.5.dp, color = borderColor, shape = RoundedCornerShape(10.dp))
                    .clickable(
                        enabled = isKeyClickable,
                        interactionSource = remember { MutableInteractionSource() },
                        indication = ripple(color = Color(0xFF64B5F6))
                    ) {
                        onLetterClick(char)
                    }
                    .testTag("key_$char"),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = char.toString(),
                    fontFamily = FredokaFontFamily,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    color = textColor
                )
            }
        }
    }
}
