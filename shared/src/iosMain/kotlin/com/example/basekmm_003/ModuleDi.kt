package com.example.basekmm_003

import com.example.basekmm_003.DatabaseDriverFactory
import com.example.basekmm_003.MainDispatcher
import org.koin.core.module.Module
import org.koin.dsl.module

actual fun platformModule(): Module = module {
    single { DatabaseDriverFactory() }
    single { MainDispatcher() }
}