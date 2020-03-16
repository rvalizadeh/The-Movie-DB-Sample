package com.themoviedb.di

import org.koin.core.context.loadKoinModules

object AppModules {
    fun init() = loadKoinModules(
        listOf(
            networkModule,
            nowPlayingModule
        )
    )
}
