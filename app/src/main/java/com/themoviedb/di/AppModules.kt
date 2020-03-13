package com.themoviedb.di

import com.themoviedb.networkModule
import org.koin.core.context.loadKoinModules
import org.koin.dsl.module

object AppModules {
    fun init() = loadKoinModules(
        listOf(
            networkModule,
            nowPlayingModule
        )
    )
}
