package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.ui.screens.DictionaryScreen
import com.example.ui.screens.GameScreen
import com.example.ui.screens.HomeScreen
import com.example.ui.theme.MyApplicationTheme
import com.example.viewmodel.GameViewModel
import com.example.viewmodel.Screen

class MainActivity : ComponentActivity() {

    private val viewModel: GameViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    WordBalloonApp(
                        viewModel = viewModel,
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }
        }
    }
}

@Composable
fun WordBalloonApp(
    viewModel: GameViewModel,
    modifier: Modifier = Modifier
) {
    val currentScreen by viewModel.currentScreen.collectAsStateWithLifecycle()
    val gameState by viewModel.gameState.collectAsStateWithLifecycle()
    val solvedWords by viewModel.solvedWords.collectAsStateWithLifecycle()

    Crossfade(targetState = currentScreen, label = "screen_transition") { screen ->
        when (screen) {
            Screen.HOME -> HomeScreen(
                gameState = gameState,
                onStartGame = { grade, category ->
                    viewModel.startNewGame(grade, category)
                },
                onStartCustomGame = { word, hint ->
                    viewModel.startCustomWordGame(word, hint)
                },
                onOpenDictionary = {
                    viewModel.refreshSolvedWords()
                    viewModel.navigateTo(Screen.DICTIONARY)
                },
                onThemeChanged = { theme ->
                    viewModel.changeTheme(theme)
                },
                modifier = modifier
            )
            Screen.GAME -> GameScreen(
                gameState = gameState,
                onLetterClick = { letter ->
                    viewModel.guessLetter(letter)
                },
                onRevealLetter = {
                    viewModel.revealRandomLetter()
                },
                onToggleVowels = {
                    viewModel.toggleVowelHighlight()
                },
                onSpeakHint = {
                    viewModel.speakHint()
                },
                onSpeakWord = {
                    viewModel.speakWord()
                },
                onNextWord = {
                    viewModel.restartCurrentGame()
                },
                onRestartGame = {
                    viewModel.restartCurrentGame()
                },
                onBackToHome = {
                    viewModel.navigateTo(Screen.HOME)
                },
                modifier = modifier
            )
            Screen.DICTIONARY -> DictionaryScreen(
                solvedWords = solvedWords,
                onSpeakWord = { word ->
                    viewModel.speakWord()
                },
                onBackToHome = {
                    viewModel.navigateTo(Screen.HOME)
                },
                onStartGame = {
                    viewModel.startNewGame()
                },
                modifier = modifier
            )
        }
    }
}
