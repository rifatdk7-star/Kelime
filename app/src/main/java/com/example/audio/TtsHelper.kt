package com.example.audio

import android.content.Context
import android.speech.tts.TextToSpeech
import java.util.Locale

class TtsHelper(context: Context) : TextToSpeech.OnInitListener {

    private var tts: TextToSpeech? = TextToSpeech(context.applicationContext, this)
    private var isReady = false

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            val turkishLocale = Locale("tr", "TR")
            val result = tts?.setLanguage(turkishLocale)
            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                // Fallback to default locale
                tts?.language = Locale.getDefault()
            }
            // Slightly slower speech rate for young learners (0.85x)
            tts?.setSpeechRate(0.85f)
            tts?.setPitch(1.05f)
            isReady = true
        }
    }

    fun speak(text: String) {
        if (isReady && text.isNotBlank()) {
            tts?.stop()
            tts?.speak(text, TextToSpeech.QUEUE_FLUSH, null, "kelime_balonu_tts")
        }
    }

    fun stop() {
        tts?.stop()
    }

    fun shutdown() {
        tts?.stop()
        tts?.shutdown()
        tts = null
    }
}
