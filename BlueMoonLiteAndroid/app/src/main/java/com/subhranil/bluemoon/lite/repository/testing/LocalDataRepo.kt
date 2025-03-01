package com.subhranil.bluemoon.lite.repository.testing

import com.subhranil.bluemoon.lite.models.BasicInfo
import com.subhranil.bluemoon.lite.repository.LocalInfoRepository
import kotlinx.coroutines.delay

class LocalDataRepo: LocalInfoRepository {
    private val basicInfoList = mutableListOf<BasicInfo>()
    init {
        basicInfoList.add(
            BasicInfo("Test Host", "1.0", "1.0", "http://localhost:8080")
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