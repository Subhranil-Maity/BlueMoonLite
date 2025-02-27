package com.subhranil.bluemoon.lite.screens.select_host.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.subhranil.bluemoon.lite.R
import com.subhranil.bluemoon.lite.models.BasicInfo

@Composable
fun HostLabel(
    modifier: Modifier = Modifier,
    basicInfo: BasicInfo,
    onClick: () -> Unit
) {
    Card {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .height(50.dp)
            ,
            verticalAlignment = Alignment.CenterVertically,
            //        horizontalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(R.drawable.windows_icon),
                contentDescription = null,
                tint = Color.Blue,
                modifier = Modifier.size(100.dp)
            )
            Spacer(modifier = Modifier.width(5.dp))
            Text(
                text = basicInfo.hostName,
                fontSize = 20.sp
            )
        }
    }
}