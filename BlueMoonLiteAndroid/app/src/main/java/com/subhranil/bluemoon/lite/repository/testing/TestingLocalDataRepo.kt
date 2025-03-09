package com.subhranil.bluemoon.lite.repository.testing

import com.subhranil.bluemoon.lite.models.BasicInfo
import com.subhranil.bluemoon.lite.models.local.JustBasicInfo
import com.subhranil.bluemoon.lite.repository.LocalInfoRepository
import kotlinx.coroutines.delay

class TestingLocalDataRepo: LocalInfoRepository {
    private val basicInfoList = mutableListOf<JustBasicInfo>()
    init {
        basicInfoList.add(
            JustBasicInfo("Test Host", "http://192.168.161.114:3000")
        )
    }
    override suspend fun getAllPreviousBasicInfo(): List<JustBasicInfo> {
        delay(50)
        return basicInfoList
    }

    override suspend fun addAllBasicInfo(basicInfos: List<JustBasicInfo>) {
        basicInfoList.addAll(basicInfos)
    }
}