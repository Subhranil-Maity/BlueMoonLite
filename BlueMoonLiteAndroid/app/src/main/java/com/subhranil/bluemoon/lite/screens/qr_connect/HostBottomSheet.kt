package com.subhranil.bluemoon.lite.screens.qr_connect

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.imePadding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.subhranil.bluemoon.lite.components.DeviceInfoDisplay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HostBottomSheet(
    sheetState: SheetState,
    onAction: (action: QrScannerAction) -> Unit,
    state: QrScannerScreenState,
    navHostController: NavHostController
) {
    ModalBottomSheet(
        sheetState = sheetState,
        modifier = Modifier
            .heightIn(min = 200.dp)
            .imePadding(),
        onDismissRequest = {
            onAction(QrScannerAction.OnBottomSheetDismissed)
        }
    ) {
        if (state.isBasicInfoLoading) {
            Row (
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(500.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ){
                CircularProgressIndicator()
            }
        } else if(state.isError != null) {
            Text("Error: ${state.isError}")
        }else if (
            state.basicInfo != null
        ) {
            DeviceInfoDisplay(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                deviceInfo = state.basicInfo
            )
            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Absolute.Right
            ) {
                Button(onClick = {
                    onAction(
                        QrScannerAction.OnConnectButtonPressed(
                            navHostController,
                            state.basicInfo
                        )
                    )
                }) {
                    Text("Connect", fontSize = 20.sp)
                }
            }
        } else {
            onAction(QrScannerAction.OnBottomSheetDismissed)
        }

    }
}