package com.hadadas.pokemons

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.hadadas.pokemons.ui.main.MainFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}