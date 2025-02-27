package com.subhranil.bluemoon.lite.screens.select_host

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.subhranil.bluemoon.lite.screens.select_host.subscreens.ShowConnectScreen
import com.subhranil.bluemoon.lite.screens.select_host.subscreens.ShowHistoryScreen
import kotlinx.serialization.Serializable

@Serializable
object SelectHostScreenRoute

@Composable
fun SelectHostScreen(
    modifier: Modifier = Modifier,
    viewModel: com.subhranil.bluemoon.lite.screens.select_host.SelectHostViewModel,
    navController: NavHostController
) {
    val state = viewModel.state.collectAsStateWithLifecycle().value
    val onAction = viewModel::onAction
    Column(
        modifier = modifier.fillMaxSize()
    ) {
        ShowConnectScreen(onAction, navController)
        ShowHistoryScreen(state, onAction)
    }
}




