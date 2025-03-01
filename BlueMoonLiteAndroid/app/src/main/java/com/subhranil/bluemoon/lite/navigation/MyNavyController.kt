package com.subhranil.bluemoon.lite.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.subhranil.bluemoon.lite.explorer.ExplorerScreen
import com.subhranil.bluemoon.lite.explorer.ExplorerScreenRoute
import com.subhranil.bluemoon.lite.screens.qr_connect.QrScannerCameraScreen
import com.subhranil.bluemoon.lite.screens.qr_connect.QrScannerCameraScreenRoute
import com.subhranil.bluemoon.lite.screens.select_host.SelectHostScreen
import com.subhranil.bluemoon.lite.screens.select_host.SelectHostScreenRoute
import org.koin.androidx.compose.koinViewModel

@Composable
fun MyNavHostController(
    navController: NavHostController,
) {
    NavHost(
        navController = navController,
        startDestination = SelectHostScreenRoute
    ){
        composable<SelectHostScreenRoute> {
            SelectHostScreen(
                viewModel = koinViewModel(),
                navController = navController
            )
        }
        composable<QrScannerCameraScreenRoute> {
            QrScannerCameraScreen(
                viewModel = koinViewModel(),
                navHostController = navController
            )
        }
        composable<ExplorerScreenRoute> {

            ExplorerScreen(
                viewModel = koinViewModel(),
                navHostController = navController
            )
        }
    }
}