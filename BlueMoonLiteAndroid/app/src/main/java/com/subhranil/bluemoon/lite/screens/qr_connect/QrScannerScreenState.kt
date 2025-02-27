package com.subhranil.bluemoon.lite.screens.qr_connect

import com.subhranil.bluemoon.lite.models.BasicInfo


data class QrScannerScreenState(
    val shouldShowBottomSheet: Boolean = false,
    val isBasicInfoLoading: Boolean = false,
    val isError: String? = null,
    val basicInfo: BasicInfo? = null,
)