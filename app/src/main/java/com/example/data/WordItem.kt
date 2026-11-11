package com.example.data

import com.example.model.GradeLevel
import com.example.model.WordCategory

data class WordItem(
    val id: String,
    val word: String, // Uppercase Turkish
    val hint: String, // Child-friendly pedagogical clue
    val funFact: String, // Educational fact upon winning
    val gradeLevel: GradeLevel,
    val category: WordCategory
)
