package com.subhranil.bluemoon.lite.repository

import com.subhranil.bluemoon.lite.models.BasicInfo
import com.subhranil.bluemoon.lite.models.Drive
import com.subhranil.bluemoon.lite.models.FsEntry
import com.subhranil.bluemoon.lite.utils.result.MyResult
import com.subhranil.bluemoon.lite.utils.result.RemoteError

interface ServerRepository {
    suspend fun isServerReachable(url: String): Boolean
    suspend fun getBasicInfo(url: String): MyResult<BasicInfo, RemoteError>
    suspend fun getDrives(url: String): MyResult<List<Drive>, RemoteError>
    suspend fun getEntries(url: String, path: String): MyResult<List<FsEntry>, RemoteError>
}