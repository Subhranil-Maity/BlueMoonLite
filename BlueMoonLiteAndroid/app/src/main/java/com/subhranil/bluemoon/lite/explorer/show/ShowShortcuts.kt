package com.subhranil.bluemoon.lite.explorer.show

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.subhranil.bluemoon.lite.components.LinearDrive
import com.subhranil.bluemoon.lite.components.PullToRefreshLazyColumn
import com.subhranil.bluemoon.lite.explorer.ExplorerActions
import com.subhranil.bluemoon.lite.explorer.ExplorerState

@Composable
fun ShowShortcuts(state: ExplorerState, onAction: (ExplorerActions) -> Unit) {
    Column(
    ) {
        ShowDrives(state, onAction)
        ShowOtherLocations(state, onAction)
    }

}

@Composable
fun ShowOtherLocations(state: ExplorerState, onAction: (ExplorerActions) -> Unit) {
    Column {
    }
}

@Composable
fun ShowDrives(
    state: ExplorerState,
    onAction: (ExplorerActions) -> Unit
) {
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
