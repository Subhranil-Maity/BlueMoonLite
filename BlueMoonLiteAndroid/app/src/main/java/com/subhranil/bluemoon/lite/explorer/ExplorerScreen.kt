package com.subhranil.bluemoon.lite.explorer

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import kotlinx.serialization.Serializable

@Serializable
object ExplorerScreenRoute

@Composable
fun ExplorerScreen(
    modifier: Modifier = Modifier,
    viewModel: ExplorerViewModel,
    navHostController: NavHostController
) {

}