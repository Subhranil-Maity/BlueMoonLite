package com.subhranil.bluemoon.lite.screens.select_host

import com.subhranil.bluemoon.lite.models.BasicInfo

data class SelectHostScreenState (
    var activeHosts: List<BasicInfo> = emptyList(),
)