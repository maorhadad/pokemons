package com.hadadas.pokemons

import android.app.Application
import android.util.Log
import com.hadadas.pokemons.abstraction.IRepository
import com.hadadas.pokemons.abstraction.IRepositoryAccess
import com.hadadas.pokemons.repositories.AppRepository


class App : Application(), IRepositoryAccess {

    private lateinit var repository: IRepository
    override fun onCreate() {
        super.onCreate()
        Log.d("App", "${getString(R.string.app_name)} is running")
        repository = AppRepository(this)
    }

    override fun getRepository() = repository
}