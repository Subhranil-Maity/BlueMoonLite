package com.subhranil.bluemoon.lite.repository.testing

import com.subhranil.bluemoon.lite.models.BasicInfo
import com.subhranil.bluemoon.lite.repository.LocalInfoRepository
import kotlinx.coroutines.delay

class TestingLocalDataRepo: LocalInfoRepository {
    private val basicInfoList = mutableListOf<BasicInfo>()
    init {
        basicInfoList.add(
            BasicInfo("Test Host", "1.0", "1.0", "http://192.168.161.114:3000")
        )
    }
    override suspend fun getAllPreviousBasicInfo(): List<BasicInfo> {
        delay(50)
        return basicInfoList
    }

    override suspend fun addAllBasicInfo(basicInfos: List<BasicInfo>) {
        basicInfoList.addAll(basicInfos)
    }
}