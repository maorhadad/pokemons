package com.hadadas.pokemons

import android.app.Application
import android.util.Log

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        Log.d("App", "${getString(R.string.app_name)} is running")
    }
}