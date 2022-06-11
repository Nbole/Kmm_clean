package com.example.basekmm_003.di

import com.example.basekmm_003.data.remote.httpModule
import com.example.basekmm_003.data.local.movieDaoModule
import com.example.basekmm_003.data.remote.movieRemoteModule
import com.example.basekmm_003.data.movieRepositoryModule
import com.example.basekmm_003.domain.useCaseModule
import com.example.basekmm_003.platformModule
import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration

fun initKoin(appDeclaration: KoinAppDeclaration = {}) = startKoin {
    appDeclaration()
    modules(
        platformModule(),
        httpModule,
        movieRemoteModule,
        movieDaoModule,
        movieRepositoryModule,
        useCaseModule
    )
}

// called by iOS etc
fun initKoin() = initKoin{}
