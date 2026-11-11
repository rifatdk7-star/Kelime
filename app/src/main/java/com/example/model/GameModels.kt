package com.example.model

enum class GameTheme(
    val title: String,
    val iconEmoji: String,
    val description: String,
    val friendlySaveMessage: String
) {
    BALLOONS(
        title = "Renkli Balonlar",
        iconEmoji = "🎈",
        description = "Ponçik rengarenk balonlarla gökyüzünde süzülüyor!",
        friendlySaveMessage = "Ponçik paraşütünü açtı ve yumuşacık tramboline indi! Harika bir çabaydı!"
    ),
    ROCKET(
        title = "Uzay Roketi",
        iconEmoji = "🚀",
        description = "Astronot Ponçik yıldızlara gitmek için yakıt topluyor!",
        friendlySaveMessage = "Roket güvenle iniş pistine süzüldü! Bir dahaki sefere aya uçacağız!"
    ),
    FLOWER(
        title = "Sihirli Çiçek",
        iconEmoji = "🌸",
        description = "Güneşli bahçede renkli yaprakları koruyalım!",
        friendlySaveMessage = "Çiçeğimiz sulandı ve yarın tekrar açacak! Çok güzel denedin!"
    )
}

enum class GradeLevel(
    val title: String,
    val subTitle: String,
    val badge: String,
    val minLength: Int,
    val maxLength: Int
) {
    GRADE_1(
        title = "1. Sınıf",
        subTitle = "3-4 Harfli Kolay Kelimeler",
        badge = "🌱 Başlangıç",
        minLength = 3,
        maxLength = 4
    ),
    GRADE_2(
        title = "2. Sınıf",
        subTitle = "5-6 Harfli Eğlenceli Kelimeler",
        badge = "⭐ Kaşif",
        minLength = 5,
        maxLength = 6
    ),
    GRADE_3(
        title = "3. Sınıf",
        subTitle = "6-7 Harfli Zengin Kelimeler",
        badge = "🚀 Macera",
        minLength = 6,
        maxLength = 7
    ),
    GRADE_4(
        title = "4. Sınıf",
        subTitle = "7-9 Harfli Bilgi & Keşif",
        badge = "👑 Usta",
        minLength = 7,
        maxLength = 10
    )
}

enum class WordCategory(
    val id: String,
    val title: String,
    val emoji: String
) {
    ALL("all", "Karışık Macera", "🌈"),
    ANIMALS("animals", "Hayvanlar", "🐾"),
    FOOD("food", "Meyve & Yiyecek", "🍎"),
    SCHOOL("school", "Okul & Sınıf", "🎒"),
    NATURE("nature", "Uzay & Doğa", "🌍"),
    GAMES("games", "Oyun & Spor", "⚽"),
    COLORS("colors", "Renk & Şekil", "🎨")
}

enum class GameStatus {
    PLAYING,
    WON,
    LOST
}

data class GameState(
    val currentWord: String = "",
    val hint: String = "",
    val funFact: String = "",
    val category: WordCategory = WordCategory.ALL,
    val gradeLevel: GradeLevel = GradeLevel.GRADE_1,
    val guessedLetters: Set<Char> = emptySet(),
    val wrongGuessesCount: Int = 0,
    val maxWrongGuesses: Int = 6,
    val gameStatus: GameStatus = GameStatus.PLAYING,
    val currentTheme: GameTheme = GameTheme.BALLOONS,
    val totalScore: Int = 0,
    val stars: Int = 10,
    val streak: Int = 0,
    val bestStreak: Int = 0,
    val highlightVowels: Boolean = false,
    val isCustomWord: Boolean = false,
    val revealedLettersCount: Int = 0
) {
    val remainingChances: Int
        get() = (maxWrongGuesses - wrongGuessesCount).coerceAtLeast(0)

    val isWordComplete: Boolean
        get() = currentWord.isNotEmpty() && currentWord.all { char ->
            char == ' ' || guessedLetters.contains(char)
        }
}
