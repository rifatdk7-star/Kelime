package com.example.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.audio.SoundPlayer
import com.example.audio.TtsHelper
import com.example.data.WordItem
import com.example.data.WordRepository
import com.example.model.GameState
import com.example.model.GameStatus
import com.example.model.GameTheme
import com.example.model.GradeLevel
import com.example.model.WordCategory
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

enum class Screen {
    HOME,
    GAME,
    DICTIONARY
}

class GameViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = WordRepository(application)
    private val soundPlayer = SoundPlayer(application)
    private val ttsHelper = TtsHelper(application)

    private val _currentScreen = MutableStateFlow(Screen.HOME)
    val currentScreen: StateFlow<Screen> = _currentScreen.asStateFlow()

    private val _gameState = MutableStateFlow(
        GameState(
            stars = repository.getStars(),
            totalScore = repository.getHighScore()
        )
    )
    val gameState: StateFlow<GameState> = _gameState.asStateFlow()

    private val _solvedWords = MutableStateFlow<List<WordItem>>(emptyList())
    val solvedWords: StateFlow<List<WordItem>> = _solvedWords.asStateFlow()

    private var currentWordItem: WordItem? = null

    init {
        refreshSolvedWords()
    }

    fun refreshSolvedWords() {
        _solvedWords.value = repository.getSolvedWords()
    }

    fun navigateTo(screen: Screen) {
        ttsHelper.stop()
        _currentScreen.value = screen
    }

    fun startNewGame(
        grade: GradeLevel = _gameState.value.gradeLevel,
        category: WordCategory = _gameState.value.category
    ) {
        val wordItem = repository.getRandomWord(grade, category, currentWordItem?.id)
        currentWordItem = wordItem

        _gameState.update { state ->
            state.copy(
                currentWord = wordItem.word,
                hint = wordItem.hint,
                funFact = wordItem.funFact,
                category = category,
                gradeLevel = grade,
                guessedLetters = emptySet(),
                wrongGuessesCount = 0,
                gameStatus = GameStatus.PLAYING,
                isCustomWord = false,
                revealedLettersCount = 0
            )
        }

        _currentScreen.value = Screen.GAME
        soundPlayer.playStarSound()
        // Pronounce the hint in cheerful tone
        speakHint()
    }

    fun startCustomWordGame(word: String, hint: String) {
        currentWordItem = null
        _gameState.update { state ->
            state.copy(
                currentWord = word.uppercase(),
                hint = hint,
                funFact = "Bu kelimeyi arkadaşın senin için hazırladı!",
                guessedLetters = emptySet(),
                wrongGuessesCount = 0,
                gameStatus = GameStatus.PLAYING,
                isCustomWord = true,
                revealedLettersCount = 0
            )
        }
        _currentScreen.value = Screen.GAME
        soundPlayer.playStarSound()
        speakHint()
    }

    fun guessLetter(letter: Char) {
        val state = _gameState.value
        if (state.gameStatus != GameStatus.PLAYING) return
        if (state.guessedLetters.contains(letter)) return

        val newGuessed = state.guessedLetters + letter
        val isCorrect = state.currentWord.contains(letter)

        if (isCorrect) {
            soundPlayer.playCorrectLetter()
            val allLettersGuessed = state.currentWord.all { char ->
                char == ' ' || newGuessed.contains(char)
            }

            if (allLettersGuessed) {
                // VICTORY!
                handleWin(state, newGuessed)
            } else {
                _gameState.update { it.copy(guessedLetters = newGuessed) }
            }
        } else {
            // WRONG GUESS
            soundPlayer.playWrongLetter()
            val newWrongCount = state.wrongGuessesCount + 1

            if (newWrongCount >= state.maxWrongGuesses) {
                // All balloons popped -> Safe Parachute Landing
                handleLoss(state, newGuessed, newWrongCount)
            } else {
                _gameState.update {
                    it.copy(
                        guessedLetters = newGuessed,
                        wrongGuessesCount = newWrongCount
                    )
                }
            }
        }
    }

    private fun handleWin(state: GameState, guessed: Set<Char>) {
        soundPlayer.playWinFanfare()
        val earnedStars = when (state.wrongGuessesCount) {
            0 -> 3 // Perfect!
            1, 2 -> 2
            else -> 1
        }
        val points = (100 - (state.wrongGuessesCount * 15)).coerceAtLeast(30)
        val newStreak = state.streak + 1
        val newStars = state.stars + earnedStars
        val newScore = state.totalScore + points

        repository.saveStars(newStars)
        repository.saveHighScore(newScore)

        currentWordItem?.let {
            repository.markWordSolved(it.id)
            refreshSolvedWords()
        }

        _gameState.update {
            it.copy(
                guessedLetters = guessed,
                gameStatus = GameStatus.WON,
                stars = newStars,
                totalScore = newScore,
                streak = newStreak,
                bestStreak = maxOf(newStreak, state.bestStreak)
            )
        }

        viewModelScope.launch {
            ttsHelper.speak("Tebrikler! Kelimeyi buldun: ${state.currentWord}")
        }
    }

    private fun handleLoss(state: GameState, guessed: Set<Char>, wrongCount: Int) {
        soundPlayer.playSafeLanding()
        _gameState.update {
            it.copy(
                guessedLetters = guessed,
                wrongGuessesCount = wrongCount,
                gameStatus = GameStatus.LOST,
                streak = 0
            )
        }

        viewModelScope.launch {
            ttsHelper.speak("Harika çaba! Doğru kelime: ${state.currentWord}")
        }
    }

    fun revealRandomLetter(): Boolean {
        val state = _gameState.value
        if (state.gameStatus != GameStatus.PLAYING) return false
        if (state.stars < 2) return false

        val remainingLetters = state.currentWord.filter { it.isLetter() && !state.guessedLetters.contains(it) }
        if (remainingLetters.isEmpty()) return false

        val chosen = remainingLetters.random()
        val newStars = state.stars - 2
        repository.saveStars(newStars)

        _gameState.update {
            it.copy(
                stars = newStars,
                revealedLettersCount = it.revealedLettersCount + 1
            )
        }
        guessLetter(chosen)
        return true
    }

    fun toggleVowelHighlight() {
        _gameState.update { it.copy(highlightVowels = !it.highlightVowels) }
    }

    fun changeTheme(newTheme: GameTheme) {
        _gameState.update { it.copy(currentTheme = newTheme) }
    }

    fun speakHint() {
        val text = _gameState.value.hint
        if (text.isNotBlank()) {
            ttsHelper.speak("İpucu: $text")
        }
    }

    fun speakWord() {
        val text = _gameState.value.currentWord
        if (text.isNotBlank()) {
            ttsHelper.speak(text)
        }
    }

    fun restartCurrentGame() {
        startNewGame(_gameState.value.gradeLevel, _gameState.value.category)
    }

    override fun onCleared() {
        super.onCleared()
        ttsHelper.shutdown()
    }
}
