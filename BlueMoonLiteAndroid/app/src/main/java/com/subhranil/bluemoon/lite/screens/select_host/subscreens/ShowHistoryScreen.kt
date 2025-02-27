package com.subhranil.bluemoon.lite.screens.select_host.subscreens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.subhranil.bluemoon.lite.screens.select_host.SelectHostScreenActions
import com.subhranil.bluemoon.lite.screens.select_host.SelectHostScreenState
import com.subhranil.bluemoon.lite.screens.select_host.components.HostLabel

@Composable
fun ColumnScope.ShowHistoryScreen(
    state: SelectHostScreenState,
    onAction: (SelectHostScreenActions) -> Unit
) {
    Column (
        Modifier.weight(1f)
            .fillMaxWidth()
            .padding(16.dp)
    ){
        Text("Connection History", style = MaterialTheme.typography.headlineLarge)
        AnimatedVisibility(state.activeHosts.isNotEmpty()) {
            state.activeHosts.forEach { info->
                HostLabel(basicInfo = info) {

                }
            }
        }

    }
}