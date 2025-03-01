package com.subhranil.bluemoon.lite.explorer

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.OnBackPressedCallback
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.subhranil.bluemoon.lite.components.LinearDrive
import com.subhranil.bluemoon.lite.components.PullToRefreshLazyColumn
import com.subhranil.bluemoon.lite.explorer.components.AppBar
import com.subhranil.bluemoon.lite.models.BasicInfo
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.Serializable
import kotlin.coroutines.coroutineContext

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
    LaunchedEffect(true) {
        viewModel.loadDrives()
    }
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val backCallback = remember {
        object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
//                if (state.onWhichExplorer == OnWhichExplorer.FILE_SYSTEM) {
                    onAction(ExplorerActions.OnBack(navHostController))
//                }
//                else{
//                    Toast.makeText(context, "Press again to exit", Toast.LENGTH_SHORT).show()
//                    scope.launch {
//                        delay(2000)
//                        isEnabled = true
//                    }
//                    isEnabled = false
//                }
            }
        }
    }
    SideEffect {
        (context as? ComponentActivity)?.onBackPressedDispatcher?.addCallback(backCallback)
    }

    Scaffold(
        modifier.fillMaxSize(),
        topBar = {
            AppBar(title = state.title)
        },
    ) { inn ->
        Column(modifier = Modifier.padding(inn)) {
//            Text(state.currentPath.getAbsolutePath())
//            Text(state.onWhichExplorer.toString())
            when (state.onWhichExplorer) {
                OnWhichExplorer.DRIVE -> ShowDrives(state, onAction)
                OnWhichExplorer.FILE_SYSTEM -> ShowFileSystem(state, onAction)
            }

        }

    }
}

@Composable
fun ShowFileSystem(state: ExplorerState, onAction: (ExplorerActions) -> Unit) {
    Column {
        Text(text = "File System")
        Text(text = state.currentPath.getAbsolutePath())
    }
}

@Composable
fun ShowDrives(state: ExplorerState, onAction: (ExplorerActions) -> Unit) {
    PullToRefreshLazyColumn(
        modifier = Modifier,
        items = state.drives,
        isRefreshing = state.isRefreshing,
        onRefresh = {
            onAction(ExplorerActions.Refresh)
        },
        content = {
            LinearDrive(drive = it) {
                onAction(ExplorerActions.SelectDrive(it))
            }
        }
    )
}
