package com.subhranil.bluemoon.lite.di

import androidx.lifecycle.SavedStateHandle
import com.subhranil.bluemoon.lite.explorer.ExplorerViewModel
import com.subhranil.bluemoon.lite.repository.LocalInfoRepository
import com.subhranil.bluemoon.lite.repository.ServerRepository
import com.subhranil.bluemoon.lite.repository.testing.TestingLocalDataRepo
import com.subhranil.bluemoon.lite.repository.testing.TestingServerRepo
import com.subhranil.bluemoon.lite.screens.qr_connect.QrScannerViewModel
import com.subhranil.bluemoon.lite.screens.select_host.SelectHostViewModel
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.bind
import org.koin.dsl.module
import com.subhranil.bluemoon.lite.repository.actual.remote.RemoteServerRepo
import com.subhranil.bluemoon.lite.repository.actual.LocalDataStore
import com.subhranil.bluemoon.lite.repository.actual.remote.provideHttpClient
import org.koin.core.module.dsl.viewModelOf

var appViewModelModule = module {
    viewModelOf(::SelectHostViewModel)
    viewModelOf(::QrScannerViewModel)
    viewModelOf(::ExplorerViewModel)
}

var singletons = module {
    single { SavedStateHandle() }
}

var appActualHostModule = module {
    single { provideHttpClient() }
    singleOf(::RemoteServerRepo).bind<ServerRepository>()
    singleOf(::LocalDataStore).bind<LocalInfoRepository>()
}

var appTestingRemoteHostModule = module {
    singleOf(::TestingServerRepo).bind<ServerRepository>()
    singleOf(::TestingLocalDataRepo).bind<LocalInfoRepository>()
}