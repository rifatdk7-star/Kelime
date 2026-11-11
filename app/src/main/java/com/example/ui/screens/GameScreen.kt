package com.example.ui.screens

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
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
import androidx.compose.ui.window.Dialog
import com.example.model.GameState
import com.example.model.GameStatus
import com.example.model.GameTheme
import com.example.ui.components.BalloonStage
import com.example.ui.components.ConfettiOverlay
import com.example.ui.components.FlowerStage
import com.example.ui.components.RocketStage
import com.example.ui.components.TurkishKeyboard
import com.example.ui.components.WordDisplay
import com.example.ui.theme.FredokaFontFamily
import com.example.ui.theme.NunitoFontFamily

@Composable
fun GameScreen(
    gameState: GameState,
    onLetterClick: (Char) -> Unit,
    onRevealLetter: () -> Unit,
    onToggleVowels: () -> Unit,
    onSpeakHint: () -> Unit,
    onSpeakWord: () -> Unit,
    onNextWord: () -> Unit,
    onRestartGame: () -> Unit,
    onBackToHome: () -> Unit,
    modifier: Modifier = Modifier
) {
    BackHandler(onBack = onBackToHome)

    Box(modifier = modifier.fillMaxSize()) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = Color(0xFFF7FAFD)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .statusBarsPadding()
                    .navigationBarsPadding()
                    .padding(horizontal = 10.dp)
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Top Header Bar
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(
                        onClick = onBackToHome,
                        modifier = Modifier
                            .size(42.dp)
                            .shadow(2.dp, CircleShape)
                            .background(Color.White, CircleShape)
                            .testTag("back_to_home_btn")
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Geri",
                            tint = Color(0xFF37474F)
                        )
                    }

                    // Grade & Mode Title
                    Box(
                        modifier = Modifier
                            .background(Color(0xFFE1F5FE), RoundedCornerShape(16.dp))
                            .border(1.dp, Color(0xFF81D4FA), RoundedCornerShape(16.dp))
                            .padding(horizontal = 12.dp, vertical = 6.dp)
                    ) {
                        Text(
                            text = if (gameState.isCustomWord) "👥 Arkadaş Modu" else "${gameState.gradeLevel.title} • ${gameState.category.title}",
                            fontFamily = FredokaFontFamily,
                            fontWeight = FontWeight.Bold,
                            fontSize = 13.sp,
                            color = Color(0xFF0277BD)
                        )
                    }

                    // Stars Counter Pill
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .background(Color(0xFFFFF8E1), RoundedCornerShape(16.dp))
                            .border(1.dp, Color(0xFFFFD54F), RoundedCornerShape(16.dp))
                            .padding(horizontal = 10.dp, vertical = 6.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Star,
                            contentDescription = "Yıldızlar",
                            tint = Color(0xFFFFA000),
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "${gameState.stars}",
                            fontFamily = FredokaFontFamily,
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp,
                            color = Color(0xFFE65100)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(4.dp))

                // The Child-Friendly Visual Stage (Hangman Replacement)
                when (gameState.currentTheme) {
                    GameTheme.BALLOONS -> BalloonStage(
                        wrongGuesses = gameState.wrongGuessesCount,
                        maxWrongGuesses = gameState.maxWrongGuesses,
                        gameStatus = gameState.gameStatus
                    )
                    GameTheme.ROCKET -> RocketStage(
                        wrongGuesses = gameState.wrongGuessesCount,
                        maxWrongGuesses = gameState.maxWrongGuesses,
                        gameStatus = gameState.gameStatus
                    )
                    GameTheme.FLOWER -> FlowerStage(
                        wrongGuesses = gameState.wrongGuessesCount,
                        maxWrongGuesses = gameState.maxWrongGuesses,
                        gameStatus = gameState.gameStatus
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                // Hint & Tools Box
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(18.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                ) {
                    Column(modifier = Modifier.padding(10.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.weight(1f)
                            ) {
                                Text(
                                    text = "💡 İpucu:",
                                    fontFamily = FredokaFontFamily,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 13.sp,
                                    color = Color(0xFFF57F17)
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = gameState.hint,
                                    fontFamily = NunitoFontFamily,
                                    fontWeight = FontWeight.SemiBold,
                                    fontSize = 13.sp,
                                    color = Color(0xFF37474F),
                                    maxLines = 2
                                )
                            }

                            // Read Hint Aloud Button
                            IconButton(
                                onClick = onSpeakHint,
                                modifier = Modifier
                                    .size(36.dp)
                                    .background(Color(0xFFE1F5FE), CircleShape)
                                    .testTag("speak_hint_btn")
                            ) {
                                Icon(
                                    imageVector = Icons.Default.VolumeUp,
                                    contentDescription = "İpucunu Seslendir",
                                    tint = Color(0xFF0288D1),
                                    modifier = Modifier.size(20.dp)
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(6.dp))

                        // Hint Tools (Reveal Letter & Vowels Filter)
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            // Reveal Letter Button (costs 2 stars)
                            Box(
                                modifier = Modifier
                                    .weight(1f)
                                    .clip(RoundedCornerShape(12.dp))
                                    .background(
                                        if (gameState.stars >= 2 && gameState.gameStatus == GameStatus.PLAYING) {
                                            Color(0xFFFFF8E1)
                                        } else Color(0xFFEEEEEE)
                                    )
                                    .border(
                                        1.dp,
                                        if (gameState.stars >= 2) Color(0xFFFFD54F) else Color(0xFFE0E0E0),
                                        RoundedCornerShape(12.dp)
                                    )
                                    .clickable(
                                        enabled = gameState.stars >= 2 && gameState.gameStatus == GameStatus.PLAYING
                                    ) { onRevealLetter() }
                                    .padding(vertical = 6.dp, horizontal = 8.dp)
                                    .testTag("reveal_letter_btn"),
                                contentAlignment = Alignment.Center
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(
                                        imageVector = Icons.Default.Lightbulb,
                                        contentDescription = null,
                                        tint = Color(0xFFFFA000),
                                        modifier = Modifier.size(16.dp)
                                    )
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text(
                                        text = "Harf Aç (2⭐)",
                                        fontFamily = FredokaFontFamily,
                                        fontWeight = FontWeight.SemiBold,
                                        fontSize = 11.sp,
                                        color = Color(0xFFE65100)
                                    )
                                }
                            }

                            // Highlight Vowels Toggle
                            Box(
                                modifier = Modifier
                                    .weight(1f)
                                    .clip(RoundedCornerShape(12.dp))
                                    .background(
                                        if (gameState.highlightVowels) Color(0xFFE8F5E9) else Color(0xFFF5F5F5)
                                    )
                                    .border(
                                        1.dp,
                                        if (gameState.highlightVowels) Color(0xFF66BB6A) else Color(0xFFE0E0E0),
                                        RoundedCornerShape(12.dp)
                                    )
                                    .clickable { onToggleVowels() }
                                    .padding(vertical = 6.dp, horizontal = 8.dp)
                                    .testTag("toggle_vowels_btn"),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = if (gameState.highlightVowels) "🌟 Sesli Harfler Açık" else "🔤 Sesli Harfleri Göster",
                                    fontFamily = FredokaFontFamily,
                                    fontWeight = FontWeight.SemiBold,
                                    fontSize = 11.sp,
                                    color = if (gameState.highlightVowels) Color(0xFF2E7D32) else Color(0xFF616161)
                                )
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                // Word Letter Slots
                WordDisplay(
                    word = gameState.currentWord,
                    guessedLetters = gameState.guessedLetters,
                    gameStatus = gameState.gameStatus
                )

                Spacer(modifier = Modifier.height(10.dp))

                // Turkish Alphabet Keyboard
                TurkishKeyboard(
                    currentWord = gameState.currentWord,
                    guessedLetters = gameState.guessedLetters,
                    highlightVowels = gameState.highlightVowels,
                    enabled = gameState.gameStatus == GameStatus.PLAYING,
                    onLetterClick = onLetterClick
                )

                Spacer(modifier = Modifier.height(16.dp))
            }
        }

        // Win Celebration Confetti
        if (gameState.gameStatus == GameStatus.WON) {
            ConfettiOverlay()
        }

        // End Game Dialog (Won or Friendly Safe Landing)
        if (gameState.gameStatus != GameStatus.PLAYING) {
            GameResultDialog(
                gameState = gameState,
                onNextWord = onNextWord,
                onRestart = onRestartGame,
                onSpeakWord = onSpeakWord,
                onBackToHome = onBackToHome
            )
        }
    }
}

@Composable
private fun GameResultDialog(
    gameState: GameState,
    onNextWord: () -> Unit,
    onRestart: () -> Unit,
    onSpeakWord: () -> Unit,
    onBackToHome: () -> Unit
) {
    val isWin = gameState.gameStatus == GameStatus.WON

    Dialog(onDismissRequest = {}) {
        Card(
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            modifier = Modifier.padding(12.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Emoji Icon
                Text(
                    text = if (isWin) "🎉👑🎈" else "🪂✨🌈",
                    fontSize = 36.sp
                )

                Spacer(modifier = Modifier.height(6.dp))

                // Title
                Text(
                    text = if (isWin) "TEBRİKLER ŞAMPİYON!" else "HARİKA BİR ÇABA!",
                    fontFamily = FredokaFontFamily,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    color = if (isWin) Color(0xFF2E7D32) else Color(0xFF0288D1),
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(6.dp))

                // Subtitle
                Text(
                    text = if (isWin) {
                        "Kelimeyi başarıyla buldun!"
                    } else {
                        gameState.currentTheme.friendlySaveMessage
                    },
                    fontFamily = NunitoFontFamily,
                    fontSize = 13.sp,
                    color = Color(0xFF546E7A),
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(12.dp))

                // The Word Pill
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .background(
                            if (isWin) Color(0xFFE8F5E9) else Color(0xFFFFF3E0),
                            RoundedCornerShape(16.dp)
                        )
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                ) {
                    Text(
                        text = gameState.currentWord,
                        fontFamily = FredokaFontFamily,
                        fontWeight = FontWeight.Bold,
                        fontSize = 24.sp,
                        color = if (isWin) Color(0xFF1B5E20) else Color(0xFFE65100)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    IconButton(
                        onClick = onSpeakWord,
                        modifier = Modifier.size(32.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.VolumeUp,
                            contentDescription = "Seslendir",
                            tint = if (isWin) Color(0xFF2E7D32) else Color(0xFFE65100),
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }

                // Educational Fun Fact (Bilgi Kartı)
                if (gameState.funFact.isNotBlank()) {
                    Spacer(modifier = Modifier.height(12.dp))
                    Card(
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(containerColor = Color(0xFFF1F8E9)),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.padding(10.dp)) {
                            Text(
                                text = "🧠 Biliyor musun?",
                                fontFamily = FredokaFontFamily,
                                fontWeight = FontWeight.Bold,
                                fontSize = 12.sp,
                                color = Color(0xFF33691E)
                            )
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(
                                text = gameState.funFact,
                                fontFamily = NunitoFontFamily,
                                fontSize = 12.sp,
                                color = Color(0xFF37474F)
                            )
                        }
                    }
                }

                // Stars earned if won
                if (isWin) {
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = "⭐ +${when (gameState.wrongGuessesCount) { 0 -> 3; 1, 2 -> 2; else -> 1 }} Yıldız Kazandın! (Seri: ${gameState.streak})",
                        fontFamily = FredokaFontFamily,
                        fontWeight = FontWeight.Bold,
                        fontSize = 13.sp,
                        color = Color(0xFFFFA000)
                    )
                }

                Spacer(modifier = Modifier.height(18.dp))

                // Actions
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Button(
                        onClick = onNextWord,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (isWin) Color(0xFFFF9800) else Color(0xFF0288D1)
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp)
                            .testTag("next_word_btn"),
                        shape = RoundedCornerShape(14.dp)
                    ) {
                        Text(
                            text = if (isWin) "Sonraki Kelime 🎈" else "Tekrar Oyna 🔄",
                            fontFamily = FredokaFontFamily,
                            fontWeight = FontWeight.Bold,
                            fontSize = 15.sp,
                            color = Color.White
                        )
                    }

                    OutlinedButton(
                        onClick = onBackToHome,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(44.dp)
                            .testTag("result_home_btn"),
                        shape = RoundedCornerShape(14.dp)
                    ) {
                        Text(
                            text = "Ana Sayfaya Dön",
                            fontFamily = FredokaFontFamily,
                            fontSize = 14.sp
                        )
                    }
                }
            }
        }
    }
}
