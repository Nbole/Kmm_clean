package com.example.basekmm_003

import com.squareup.sqldelight.db.SqlDriver
import com.squareup.sqldelight.drivers.native.NativeSqliteDriver
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Runnable
import org.koin.core.module.Module
import org.koin.dsl.module
import platform.Foundation.NSRunLoop
import platform.Foundation.performBlock
import platform.UIKit.UIDevice
import kotlin.coroutines.CoroutineContext

actual class DatabaseDriverFactory {
    actual fun createDriver(): SqlDriver {
        return NativeSqliteDriver(AppDatabase.Schema, "test.db")
    }
}

actual class MainDispatcher {
    actual val dispatcher: CoroutineDispatcher = MainLoopDispatcher
}

object MainLoopDispatcher : CoroutineDispatcher() {
    override fun dispatch(context: CoroutineContext, block: Runnable) {
        NSRunLoop.mainRunLoop().performBlock { block.run() }
    }
}