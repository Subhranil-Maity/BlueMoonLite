package com.subhranil.bluemoon.lite.explorer.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.heightIn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.subhranil.bluemoon.lite.explorer.ExplorerActions
import com.subhranil.bluemoon.lite.explorer.ExplorerState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBar(
    state: ExplorerState,
    onAction: (ExplorerActions) -> Unit,
) {
    TopAppBar(
        title = {
            Column {
                Text(text = state.title)
                Text(text = state.currentPath.getAbsolutePath(), style = MaterialTheme.typography.titleSmall)
            }
        },
    )
}