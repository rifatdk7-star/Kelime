package com.example.audio

import android.content.Context
import android.media.AudioAttributes
import android.media.AudioFormat
import android.media.AudioTrack
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.math.sin

class SoundPlayer(private val context: Context) {

    private val scope = CoroutineScope(Dispatchers.Default)

    private val vibrator: Vibrator? = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
        val manager = context.getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as? VibratorManager
        manager?.defaultVibrator
    } else {
        @Suppress("DEPRECATION")
        context.getSystemService(Context.VIBRATOR_SERVICE) as? Vibrator
    }

    fun playCorrectLetter() {
        // Cheerful ascending two-tone chime
        vibrateSuccess(short = true)
        scope.launch {
            playTone(523.25, 120) // C5
            playTone(659.25, 150) // E5
        }
    }

    fun playWrongLetter() {
        // Cute playful pop/boop
        vibrateError()
        scope.launch {
            playTone(330.0, 90) // E4
            playTone(260.0, 110) // C4
        }
    }

    fun playWinFanfare() {
        // Joyful victory fanfare: C5, E5, G5, C6!
        vibrateCelebration()
        scope.launch {
            playTone(523.25, 100) // C5
            playTone(659.25, 100) // E5
            playTone(783.99, 120) // G5
            playTone(1046.50, 280) // C6
        }
    }

    fun playSafeLanding() {
        // Soft parachute descending flute chime
        scope.launch {
            playTone(587.33, 150) // D5
            playTone(493.88, 150) // B4
            playTone(440.00, 200) // A4
        }
    }

    fun playStarSound() {
        // Magic sparkle sound
        vibrateSuccess(short = true)
        scope.launch {
            playTone(880.0, 80) // A5
            playTone(1174.66, 120) // D6
        }
    }

    private fun playTone(frequencyHz: Double, durationMs: Int) {
        try {
            val sampleRate = 44100
            val numSamples = (durationMs * sampleRate / 1000)
            val buffer = ShortArray(numSamples)

            for (i in 0 until numSamples) {
                // Envelope for smooth attack & release to avoid click sounds
                val envelope = when {
                    i < 200 -> i / 200.0
                    i > numSamples - 400 -> (numSamples - i) / 400.0
                    else -> 1.0
                }
                val angle = 2.0 * Math.PI * i * frequencyHz / sampleRate
                val sample = (sin(angle) * envelope * Short.MAX_VALUE * 0.4).toInt()
                buffer[i] = sample.toShort()
            }

            val audioAttributes = AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_GAME)
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .build()

            val audioFormat = AudioFormat.Builder()
                .setSampleRate(sampleRate)
                .setEncoding(AudioFormat.ENCODING_PCM_16BIT)
                .setChannelMask(AudioFormat.CHANNEL_OUT_MONO)
                .build()

            val track = AudioTrack.Builder()
                .setAudioAttributes(audioAttributes)
                .setAudioFormat(audioFormat)
                .setBufferSizeInBytes(buffer.size * 2)
                .setTransferMode(AudioTrack.MODE_STATIC)
                .build()

            track.write(buffer, 0, buffer.size)
            track.play()
            Thread.sleep(durationMs.toLong() + 20)
            track.stop()
            track.release()
        } catch (_: Exception) {
            // AudioTrack fallback
        }
    }

    private fun vibrateSuccess(short: Boolean = false) {
        try {
            if (vibrator?.hasVibrator() == true) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    val duration = if (short) 30L else 70L
                    vibrator.vibrate(VibrationEffect.createOneShot(duration, VibrationEffect.DEFAULT_AMPLITUDE))
                } else {
                    @Suppress("DEPRECATION")
                    vibrator.vibrate(40)
                }
            }
        } catch (_: Exception) {}
    }

    private fun vibrateError() {
        try {
            if (vibrator?.hasVibrator() == true) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    vibrator.vibrate(VibrationEffect.createWaveform(longArrayOf(0, 40, 50, 40), -1))
                } else {
                    @Suppress("DEPRECATION")
                    vibrator.vibrate(60)
                }
            }
        } catch (_: Exception) {}
    }

    private fun vibrateCelebration() {
        try {
            if (vibrator?.hasVibrator() == true) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    vibrator.vibrate(VibrationEffect.createWaveform(longArrayOf(0, 60, 60, 60, 60, 120), -1))
                } else {
                    @Suppress("DEPRECATION")
                    vibrator.vibrate(200)
                }
            }
        } catch (_: Exception) {}
    }
}
