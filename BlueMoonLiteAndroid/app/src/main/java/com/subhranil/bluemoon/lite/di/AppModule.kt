package com.subhranil.bluemoon.lite.di

import androidx.lifecycle.SavedStateHandle
import com.subhranil.bluemoon.lite.explorer.ExplorerViewModel
import com.subhranil.bluemoon.lite.repository.LocalInfoRepository
import com.subhranil.bluemoon.lite.repository.ServerRepository
import com.subhranil.bluemoon.lite.repository.testing.LocalDataRepo
import com.subhranil.bluemoon.lite.repository.testing.RemoteServerRepo
import com.subhranil.bluemoon.lite.screens.qr_connect.QrScannerViewModel
import com.subhranil.bluemoon.lite.screens.select_host.SelectHostViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.bind
import org.koin.dsl.module


var appViewModelModule = module {
    viewModel { SelectHostViewModel(get(), get(), get()) }
    viewModel { QrScannerViewModel(get(), get()) }
    viewModel { ExplorerViewModel(get(), get()) }
}

var singletons = module {
    single { SavedStateHandle() }
}

var appTestingRemoteHostModule = module {
    single { RemoteServerRepo() }.bind(ServerRepository::class)
    single { LocalDataRepo() }.bind(LocalInfoRepository::class)
}