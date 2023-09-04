package com.hadadas.pokemons.games.memorygame

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.hadadas.pokemons.R
import com.hadadas.pokemons.games.memorygame.ui.MemoryFragment

class MemoryActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_memory)
        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.container, MemoryFragment.newInstance())
                .commitNow()
        }
    }
}