package com.example

import com.example.model.GradeLevel
import com.example.model.WordCategory
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test
import java.util.Locale

class ExampleUnitTest {

    @Test
    fun testTurkishAlphabetUppercaseConversion() {
        val trLocale = Locale("tr", "TR")
        val input = "çiçek"
        val uppercase = input.uppercase(trLocale)
        assertEquals("ÇİÇEK", uppercase)
    }

    @Test
    fun testTurkishVowelCheck() {
        val vowels = setOf('A', 'E', 'I', 'İ', 'O', 'Ö', 'U', 'Ü')
        assertTrue(vowels.contains('İ'))
        assertTrue(vowels.contains('Ö'))
        assertTrue(vowels.contains('Ü'))
        assertTrue(vowels.contains('I'))
        assertFalse(vowels.contains('K'))
        assertFalse(vowels.contains('Ş'))
    }

    @Test
    fun testWordCompletionLogic() {
        val secretWord = "KEDİ"
        val guessedLetters = setOf('K', 'E', 'D', 'İ')
        val isComplete = secretWord.all { char ->
            char == ' ' || guessedLetters.contains(char)
        }
        assertTrue(isComplete)
    }

    @Test
    fun testWordIncompleteLogic() {
        val secretWord = "TAVŞAN"
        val guessedLetters = setOf('T', 'A', 'V')
        val isComplete = secretWord.all { char ->
            char == ' ' || guessedLetters.contains(char)
        }
        assertFalse(isComplete)
    }
}
