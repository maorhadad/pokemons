package com.hadadas.pokemons.utils

import android.content.Context
import android.media.AudioManager
import android.speech.tts.TextToSpeech
import android.widget.Toast
import com.hadadas.pokemons.abstraction.INameReader

class NameReader(private var textToSpeech: TextToSpeech?, private var audioManager: AudioManager? = null) : INameReader {

    public override fun readTextToUser(context: Context, text: String) {
        if (textToSpeech?.isSpeaking == true) {
            textToSpeech?.stop()
        }
        val volume = audioManager?.getStreamVolume(AudioManager.STREAM_MUSIC)
        if (volume == 0) {
            Toast
                .makeText(context, "Phone volume is set to zero.", Toast.LENGTH_SHORT)
                .show()
        } else {
            textToSpeech?.speak(text, TextToSpeech.QUEUE_FLUSH, null, null)
        }
    }


    override fun onDestroy() {
        textToSpeech?.stop()
        textToSpeech?.shutdown()
    }
}