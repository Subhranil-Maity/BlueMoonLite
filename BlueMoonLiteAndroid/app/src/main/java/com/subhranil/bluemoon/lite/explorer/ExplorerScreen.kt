package com.subhranil.bluemoon.lite.explorer

import android.annotation.SuppressLint
import androidx.activity.OnBackPressedCallback
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.subhranil.bluemoon.lite.explorer.components.AppBar
import com.subhranil.bluemoon.lite.explorer.show.ShowShortcuts
import com.subhranil.bluemoon.lite.explorer.show.ShowFileSystem
import kotlinx.serialization.Serializable

@Serializable
object ExplorerScreenRoute

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ExplorerScreen(
    modifier: Modifier = Modifier,
    viewModel: ExplorerViewModel,
    navHostController: NavHostController,
) {
    val state = viewModel.state.collectAsStateWithLifecycle().value
    val onAction = viewModel::onAction

    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val backDispatcher = LocalOnBackPressedDispatcherOwner.current?.onBackPressedDispatcher
    val backCallback = remember {
        object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                onAction(ExplorerActions.OnBack(navHostController))
            }
        }
    }

    DisposableEffect(Unit) {
        backDispatcher?.addCallback(backCallback)
        onDispose {
            backCallback.remove()
        }
    }



    Scaffold(
        modifier.fillMaxSize(),
        topBar = {
            AppBar(state, onAction)
        },
    ) { inn ->
        Column(modifier = Modifier.padding(inn)) {
//            Text(state.currentPath.toString() + " " + state.currentPath.toString().split(SEPARATOR).size)
            when (state.onWhichExplorer) {
                OnWhichExplorer.DRIVE -> {
                    LaunchedEffect(true) {
                        viewModel.loadDrives()
                    }
                    ShowShortcuts(state, onAction)
                }
                OnWhichExplorer.FILE_SYSTEM -> {
                    LaunchedEffect(state.currentPath) {
                        viewModel.loadCurrentPath()
                    }
                    ShowFileSystem(state, onAction)
                }
            }

        }

    }
}


