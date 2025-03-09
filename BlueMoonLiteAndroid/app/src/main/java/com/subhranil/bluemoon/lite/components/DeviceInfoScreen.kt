package com.subhranil.bluemoon.lite.components

import androidx.compose.foundation.layout.Arrangement.Center
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.subhranil.bluemoon.lite.R
import com.subhranil.bluemoon.lite.models.BasicInfo


@Composable
fun DeviceInfoDisplay(
    modifier: Modifier = Modifier,
    deviceInfo: BasicInfo
) {
        Column(
            modifier = modifier,
            verticalArrangement = Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Icon(
                modifier = Modifier.size(64.dp),
                imageVector = ImageVector.vectorResource(R.drawable.windows_icon),
                contentDescription = null,
                tint = Color.Blue
            )

            Text(text = deviceInfo.hostName, style = MaterialTheme.typography.titleLarge)
            Text(text = "Host Version: " + deviceInfo.hostVersion, fontSize = 10.sp)
            Text(text = "Server Version: " + deviceInfo.serverVersion, fontSize = 10.sp)
        }
}

@Preview
@Composable
private fun DeviceInfoScreenPreview() {
    val basicInfo = BasicInfo(
        hostName = "Test Host",
        hostVersion = "1.0.0",
        serverVersion = "1.0.0",
        hostUrl = "http://localhost:8080",
        hostOsName = "Unknown"
    )
    DeviceInfoDisplay(deviceInfo = basicInfo)
}