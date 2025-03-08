package com.subhranil.bluemoon.lite.explorer.show

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.key
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.subhranil.bluemoon.lite.components.LinearEntry
import com.subhranil.bluemoon.lite.components.PullToRefreshLazyColumn
import com.subhranil.bluemoon.lite.explorer.ExplorerActions
import com.subhranil.bluemoon.lite.explorer.ExplorerState

@Composable
fun ShowFileSystem(state: ExplorerState, onAction: (ExplorerActions) -> Unit) {
    val listState = rememberLazyListState()
    PullToRefreshLazyColumn(
        modifier = Modifier,
        items = state.currentPathEntries,
        isRefreshing = state.isRefreshing,
        onRefresh = {
            onAction(ExplorerActions.Refresh)
        },
        content = {
            LinearEntry(entry = it) {
                onAction(ExplorerActions.OnSelectedEntry(it))
            }
        },
        key = { it.absolutePath },
        lazyListState = listState
    )
}