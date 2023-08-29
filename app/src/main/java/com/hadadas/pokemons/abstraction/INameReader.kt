package com.hadadas.pokemons.abstraction

import android.content.Context

interface INameReader {

    fun readTextToUser(context: Context, text: String)
    fun onDestroy()
}