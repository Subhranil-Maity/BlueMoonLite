package com.subhranil.bluemoon.lite.screens.select_host

import com.subhranil.bluemoon.lite.models.BasicInfo
import com.subhranil.bluemoon.lite.models.local.JustBasicInfo

data class SelectHostScreenState (
    var activeHosts: List<JustBasicInfo> = emptyList(),
)