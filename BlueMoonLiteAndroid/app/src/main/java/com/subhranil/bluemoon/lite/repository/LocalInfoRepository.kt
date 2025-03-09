package com.subhranil.bluemoon.lite.repository

import com.subhranil.bluemoon.lite.models.BasicInfo
import com.subhranil.bluemoon.lite.models.local.JustBasicInfo

interface LocalInfoRepository {
    suspend fun getAllPreviousBasicInfo(): List<JustBasicInfo>
    suspend fun addAllBasicInfo(basicInfos: List<JustBasicInfo>)
}