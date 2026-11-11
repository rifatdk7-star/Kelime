package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.ui.theme.FredokaFontFamily
import com.example.ui.theme.NunitoFontFamily
import java.util.Locale

@Composable
fun CustomWordDialog(
    onDismiss: () -> Unit,
    onSubmit: (word: String, hint: String) -> Unit
) {
    var wordInput by remember { mutableStateOf("") }
    var hintInput by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    val focusManager = LocalFocusManager.current

    Dialog(onDismissRequest = onDismiss) {
        Card(
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            modifier = Modifier.padding(16.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "👥 Arkadaşına Sor!",
                    fontFamily = FredokaFontFamily,
                    fontWeight = FontWeight.Bold,
                    fontSize = 22.sp,
                    color = Color(0xFF0288D1)
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = "Gizli bir kelime ve ipucu yaz. Sonra telefonu arkadaşına ver, bilsin!",
                    fontFamily = NunitoFontFamily,
                    fontSize = 13.sp,
                    color = Color(0xFF546E7A)
                )

                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = wordInput,
                    onValueChange = { input ->
                        val trLocale = Locale("tr", "TR")
                        val filtered = input.uppercase(trLocale).filter { it.isLetter() || it == ' ' }
                        if (filtered.length <= 12) {
                            wordInput = filtered
                            errorMessage = null
                        }
                    },
                    label = { Text("Gizli Kelime (Örn: ÇİLEK)", fontFamily = NunitoFontFamily) },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(
                        capitalization = KeyboardCapitalization.Characters,
                        imeAction = ImeAction.Next
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("custom_word_input"),
                    shape = RoundedCornerShape(12.dp)
                )

                Spacer(modifier = Modifier.height(10.dp))

                OutlinedTextField(
                    value = hintInput,
                    onValueChange = {
                        hintInput = it
                        errorMessage = null
                    },
                    label = { Text("Arkadaşına İpucu (Örn: Kırmızı tatlı meyve)", fontFamily = NunitoFontFamily) },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(
                        capitalization = KeyboardCapitalization.Sentences,
                        imeAction = ImeAction.Done
                    ),
                    keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() }),
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("custom_hint_input"),
                    shape = RoundedCornerShape(12.dp)
                )

                if (errorMessage != null) {
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = errorMessage ?: "",
                        fontFamily = NunitoFontFamily,
                        fontSize = 12.sp,
                        color = Color(0xFFD32F2F)
                    )
                }

                Spacer(modifier = Modifier.height(18.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    OutlinedButton(
                        onClick = onDismiss,
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text("Vazgeç", fontFamily = FredokaFontFamily)
                    }

                    Button(
                        onClick = {
                            val trimmedWord = wordInput.trim()
                            val trimmedHint = hintInput.trim()
                            if (trimmedWord.length < 2) {
                                errorMessage = "Kelime en az 2 harfli olmalıdır!"
                            } else if (trimmedHint.isEmpty()) {
                                errorMessage = "Lütfen arkadaşın için bir ipucu yaz!"
                            } else {
                                onSubmit(trimmedWord, trimmedHint)
                            }
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF43A047)),
                        modifier = Modifier
                            .weight(1.3f)
                            .testTag("start_custom_game_btn"),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text("Hazır! 🎈", fontFamily = FredokaFontFamily, color = Color.White)
                    }
                }
            }
        }
    }
}
