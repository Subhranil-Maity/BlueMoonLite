package com.subhranil.bluemoon.lite.repository

import com.subhranil.bluemoon.lite.models.BasicInfo

interface LocalInfoRepository {
    suspend fun getAllPreviousBasicInfo(): List<BasicInfo>
    suspend fun addAllBasicInfo(basicInfos: List<BasicInfo>)
}