package com.example.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.model.GameState
import com.example.model.GameTheme
import com.example.model.GradeLevel
import com.example.model.WordCategory
import com.example.ui.components.CustomWordDialog
import com.example.ui.theme.FredokaFontFamily
import com.example.ui.theme.NunitoFontFamily
import com.example.viewmodel.Screen

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun HomeScreen(
    gameState: GameState,
    onStartGame: (GradeLevel, WordCategory) -> Unit,
    onStartCustomGame: (String, String) -> Unit,
    onOpenDictionary: () -> Unit,
    onThemeChanged: (GameTheme) -> Unit,
    modifier: Modifier = Modifier
) {
    var selectedGrade by remember { mutableStateOf(gameState.gradeLevel) }
    var selectedCategory by remember { mutableStateOf(gameState.category) }
    var showCustomWordDialog by remember { mutableStateOf(false) }

    if (showCustomWordDialog) {
        CustomWordDialog(
            onDismiss = { showCustomWordDialog = false },
            onSubmit = { word, hint ->
                showCustomWordDialog = false
                onStartCustomGame(word, hint)
            }
        )
    }

    Surface(
        modifier = modifier.fillMaxSize(),
        color = Color(0xFFF5F9FD)
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .navigationBarsPadding()
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            // Header: Stars & Score Bar
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Stars Pill
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .background(Color(0xFFFFF8E1), RoundedCornerShape(20.dp))
                            .border(1.5.dp, Color(0xFFFFD54F), RoundedCornerShape(20.dp))
                            .padding(horizontal = 14.dp, vertical = 6.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Star,
                            contentDescription = "Yıldızlar",
                            tint = Color(0xFFFFA000),
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "${gameState.stars}",
                            fontFamily = FredokaFontFamily,
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp,
                            color = Color(0xFFE65100)
                        )
                    }

                    // Score & Streak Pill
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .background(Color(0xFFE8F5E9), RoundedCornerShape(20.dp))
                            .border(1.5.dp, Color(0xFFA5D6A7), RoundedCornerShape(20.dp))
                            .padding(horizontal = 14.dp, vertical = 6.dp)
                    ) {
                        Text(
                            text = "🏆 Puan: ${gameState.totalScore}",
                            fontFamily = FredokaFontFamily,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 14.sp,
                            color = Color(0xFF2E7D32)
                        )
                        if (gameState.bestStreak > 0) {
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "🔥 En İyi: ${gameState.bestStreak}",
                                fontFamily = FredokaFontFamily,
                                fontWeight = FontWeight.Bold,
                                fontSize = 13.sp,
                                color = Color(0xFFD84315)
                            )
                        }
                    }
                }
            }

            // Hero Card: Ponçik & Welcome
            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .shadow(6.dp, RoundedCornerShape(24.dp)),
                    shape = RoundedCornerShape(24.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(
                                Brush.horizontalGradient(
                                    colors = listOf(
                                        Color(0xFFE1F5FE),
                                        Color(0xFFFFFDE7)
                                    )
                                )
                            )
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.mascot_bear),
                            contentDescription = "Ponçik",
                            modifier = Modifier
                                .size(96.dp)
                                .clip(CircleShape)
                                .shadow(4.dp, CircleShape),
                            contentScale = ContentScale.Crop
                        )

                        Spacer(modifier = Modifier.width(16.dp))

                        Column {
                            Text(
                                text = "Kelime Balonu",
                                fontFamily = FredokaFontFamily,
                                fontWeight = FontWeight.Bold,
                                fontSize = 24.sp,
                                color = Color(0xFF0277BD)
                            )
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(
                                text = "Şiddetsiz, eğlenceli ve eğitici kelime keşfi!",
                                fontFamily = NunitoFontFamily,
                                fontWeight = FontWeight.Medium,
                                fontSize = 13.sp,
                                color = Color(0xFF546E7A)
                            )
                            Spacer(modifier = Modifier.height(6.dp))
                            Box(
                                modifier = Modifier
                                    .background(Color(0xFF4FC3F7).copy(alpha = 0.25f), RoundedCornerShape(12.dp))
                                    .padding(horizontal = 8.dp, vertical = 4.dp)
                            ) {
                                Text(
                                    text = "✨ İpucunu oku, balonları uçur!",
                                    fontFamily = FredokaFontFamily,
                                    fontSize = 12.sp,
                                    color = Color(0xFF01579B)
                                )
                            }
                        }
                    }
                }
            }

            // Grade Level Selection (1., 2., 3., 4. Sınıf)
            item {
                Column {
                    Text(
                        text = "🎒 Sınıfını Seç",
                        fontFamily = FredokaFontFamily,
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        color = Color(0xFF263238)
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        GradeLevel.entries.forEach { grade ->
                            val isSelected = selectedGrade == grade
                            Box(
                                modifier = Modifier
                                    .weight(1f)
                                    .shadow(if (isSelected) 4.dp else 1.dp, RoundedCornerShape(16.dp))
                                    .clip(RoundedCornerShape(16.dp))
                                    .background(
                                        if (isSelected) Color(0xFF0288D1) else Color.White
                                    )
                                    .border(
                                        width = if (isSelected) 2.dp else 1.dp,
                                        color = if (isSelected) Color(0xFF0277BD) else Color(0xFFE0E0E0),
                                        shape = RoundedCornerShape(16.dp)
                                    )
                                    .clickable { selectedGrade = grade }
                                    .padding(vertical = 10.dp, horizontal = 4.dp)
                                    .testTag("grade_${grade.name}"),
                                contentAlignment = Alignment.Center
                            ) {
                                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                    Text(
                                        text = grade.title,
                                        fontFamily = FredokaFontFamily,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 15.sp,
                                        color = if (isSelected) Color.White else Color(0xFF37474F)
                                    )
                                    Spacer(modifier = Modifier.height(2.dp))
                                    Text(
                                        text = grade.badge,
                                        fontFamily = NunitoFontFamily,
                                        fontSize = 10.sp,
                                        color = if (isSelected) Color(0xFFE1F5FE) else Color(0xFF78909C)
                                    )
                                }
                            }
                        }
                    }
                }
            }

            // Category Selection Chips
            item {
                Column {
                    Text(
                        text = "🎨 Kategori Seç",
                        fontFamily = FredokaFontFamily,
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        color = Color(0xFF263238)
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    FlowRow(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        WordCategory.entries.forEach { cat ->
                            val isSelected = selectedCategory == cat
                            Box(
                                modifier = Modifier
                                    .shadow(if (isSelected) 3.dp else 1.dp, RoundedCornerShape(20.dp))
                                    .clip(RoundedCornerShape(20.dp))
                                    .background(
                                        if (isSelected) Color(0xFF43A047) else Color.White
                                    )
                                    .border(
                                        width = 1.dp,
                                        color = if (isSelected) Color(0xFF2E7D32) else Color(0xFFCFD8DC),
                                        shape = RoundedCornerShape(20.dp)
                                    )
                                    .clickable { selectedCategory = cat }
                                    .padding(horizontal = 14.dp, vertical = 8.dp)
                                    .testTag("cat_${cat.id}"),
                                contentAlignment = Alignment.Center
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Text(text = cat.emoji, fontSize = 14.sp)
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Text(
                                        text = cat.title,
                                        fontFamily = FredokaFontFamily,
                                        fontWeight = FontWeight.SemiBold,
                                        fontSize = 13.sp,
                                        color = if (isSelected) Color.White else Color(0xFF37474F)
                                    )
                                }
                            }
                        }
                    }
                }
            }

            // Big Play Button
            item {
                Button(
                    onClick = { onStartGame(selectedGrade, selectedCategory) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(60.dp)
                        .shadow(6.dp, RoundedCornerShape(20.dp))
                        .testTag("play_game_btn"),
                    shape = RoundedCornerShape(20.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF9800))
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.PlayArrow,
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.size(30.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "OYUNA BAŞLA! 🎈",
                            fontFamily = FredokaFontFamily,
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp,
                            color = Color.White
                        )
                    }
                }
            }

            // Theme Selector Bar
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(18.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White)
                ) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        Text(
                            text = "✨ Oyun Teması",
                            fontFamily = FredokaFontFamily,
                            fontWeight = FontWeight.Bold,
                            fontSize = 15.sp,
                            color = Color(0xFF37474F)
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            GameTheme.entries.forEach { theme ->
                                val isSelected = gameState.currentTheme == theme
                                Box(
                                    modifier = Modifier
                                        .weight(1f)
                                        .clip(RoundedCornerShape(14.dp))
                                        .background(
                                            if (isSelected) Color(0xFFE1F5FE) else Color(0xFFF5F5F5)
                                        )
                                        .border(
                                            width = if (isSelected) 1.5.dp else 0.dp,
                                            color = if (isSelected) Color(0xFF0288D1) else Color.Transparent,
                                            shape = RoundedCornerShape(14.dp)
                                        )
                                        .clickable { onThemeChanged(theme) }
                                        .padding(vertical = 8.dp),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                        Text(text = theme.iconEmoji, fontSize = 20.sp)
                                        Spacer(modifier = Modifier.height(2.dp))
                                        Text(
                                            text = theme.title,
                                            fontFamily = FredokaFontFamily,
                                            fontSize = 12.sp,
                                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                                            color = if (isSelected) Color(0xFF0277BD) else Color(0xFF616161)
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }

            // Quick Actions: 2 Player & Dictionary
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    // 2 Player Pass & Play Card
                    Card(
                        modifier = Modifier
                            .weight(1f)
                            .clickable { showCustomWordDialog = true }
                            .testTag("friend_mode_btn"),
                        shape = RoundedCornerShape(18.dp),
                        colors = CardDefaults.cardColors(containerColor = Color(0xFFE8EAF6))
                    ) {
                        Column(
                            modifier = Modifier.padding(14.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Icon(
                                imageVector = Icons.Default.People,
                                contentDescription = null,
                                tint = Color(0xFF3F51B5),
                                modifier = Modifier.size(28.dp)
                            )
                            Spacer(modifier = Modifier.height(6.dp))
                            Text(
                                text = "Arkadaşına Sor",
                                fontFamily = FredokaFontFamily,
                                fontWeight = FontWeight.Bold,
                                fontSize = 14.sp,
                                color = Color(0xFF283593)
                            )
                            Text(
                                text = "2 Kişilik Mod",
                                fontFamily = NunitoFontFamily,
                                fontSize = 11.sp,
                                color = Color(0xFF5C6BC0)
                            )
                        }
                    }

                    // Word Dictionary Card
                    Card(
                        modifier = Modifier
                            .weight(1f)
                            .clickable { onOpenDictionary() }
                            .testTag("dictionary_btn"),
                        shape = RoundedCornerShape(18.dp),
                        colors = CardDefaults.cardColors(containerColor = Color(0xFFFFF3E0))
                    ) {
                        Column(
                            modifier = Modifier.padding(14.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Icon(
                                imageVector = Icons.Default.MenuBook,
                                contentDescription = null,
                                tint = Color(0xFFE65100),
                                modifier = Modifier.size(28.dp)
                            )
                            Spacer(modifier = Modifier.height(6.dp))
                            Text(
                                text = "Kelime Defterim",
                                fontFamily = FredokaFontFamily,
                                fontWeight = FontWeight.Bold,
                                fontSize = 14.sp,
                                color = Color(0xFFBF360C)
                            )
                            Text(
                                text = "Öğrenilen Kelimeler",
                                fontFamily = NunitoFontFamily,
                                fontSize = 11.sp,
                                color = Color(0xFFF57C00)
                            )
                        }
                    }
                }
            }

            item {
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}
