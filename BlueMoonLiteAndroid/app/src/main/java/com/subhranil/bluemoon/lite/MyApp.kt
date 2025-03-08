package com.subhranil.bluemoon.lite

import android.app.Application
import com.subhranil.bluemoon.lite.di.appActualHostModule
import com.subhranil.bluemoon.lite.di.appTestingRemoteHostModule
import com.subhranil.bluemoon.lite.di.appViewModelModule
import com.subhranil.bluemoon.lite.di.singletons
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MyApp: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MyApp)
//            modules(appTestingRemoteHostModule)
            modules(appActualHostModule)
            modules(appViewModelModule)
            modules(singletons)
        }
    }
}