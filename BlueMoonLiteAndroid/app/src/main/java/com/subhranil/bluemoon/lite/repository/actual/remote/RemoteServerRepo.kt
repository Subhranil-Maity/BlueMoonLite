package com.subhranil.bluemoon.lite.repository.actual.remote

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.subhranil.bluemoon.lite.MyApp
import com.subhranil.bluemoon.lite.models.BasicInfo
import com.subhranil.bluemoon.lite.models.Drive
import com.subhranil.bluemoon.lite.models.FsEntry
import com.subhranil.bluemoon.lite.repository.ServerRepository
import com.subhranil.bluemoon.lite.repository.actual.remote.dto.BasicInfoDto
import com.subhranil.bluemoon.lite.repository.actual.remote.dto.DriveDto
import com.subhranil.bluemoon.lite.repository.actual.remote.dto.FsEntryDto
import com.subhranil.bluemoon.lite.repository.actual.remote.mapper.toBasicInfo
import com.subhranil.bluemoon.lite.repository.actual.remote.mapper.toDrive
import com.subhranil.bluemoon.lite.repository.actual.remote.mapper.toFsEntry
import com.subhranil.bluemoon.lite.utils.result.MyResult
import com.subhranil.bluemoon.lite.utils.result.RemoteError
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.http.HttpStatusCode
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.ensureActive
import org.koin.core.context.GlobalContext.get

class RemoteServerRepo(
    private val client: HttpClient
): ServerRepository {
    override suspend fun isServerReachable(url: String): Boolean {
        return try {
            val response = client.get(url)
            response.status.value == HttpStatusCode.OK.value
        }
        catch (e: Exception) {
            Log.d("RemoteServerRepo", e.message.toString())
            currentCoroutineContext().ensureActive()
            return false
        }
    }

    override suspend fun getBasicInfo(url: String): MyResult<BasicInfo, RemoteError> {
        return try {
            val response = client.get("$url/lite/get_basic_info")
            if (response.status != HttpStatusCode.OK) {
                return MyResult.Error(RemoteError.UNKNOWN_ERROR)
            }
            val body = response.body<BasicInfoDto>()
            MyResult.Success(body.toBasicInfo(url))
        }
        catch (e: Exception) {
            currentCoroutineContext().ensureActive()
            MyResult.Error(RemoteError.UNKNOWN_ERROR)
        }
    }

    override suspend fun getDrives(url: String): MyResult<List<Drive>, RemoteError> {
        return try {
            val response = client.get("$url/lite/get_drives")
            if (response.status != HttpStatusCode.OK) {
                return MyResult.Error(RemoteError.UNKNOWN_ERROR)
            }
            val body = response.body<List<DriveDto>>()
            MyResult.Success(body.map {
                it.toDrive()
            })
        }
        catch (e: Exception) {
            currentCoroutineContext().ensureActive()
            MyResult.Error(RemoteError.UNKNOWN_ERROR)
        }
    }

    override suspend fun getEntries(
        url: String,
        path: String
    ): MyResult<List<FsEntry>, RemoteError> {
        return try {
            val response = client.get("$url/lite/get_entries") {
                parameter("path", path)
            }
            if (response.status != HttpStatusCode.OK) {
                return MyResult.Error(RemoteError.UNKNOWN_ERROR)
            }
            Log.d("RemoteServerRepo", response.body<String>())
            val body = response.body<List<FsEntryDto>>()
            MyResult.Success(body.map {
                it.toFsEntry()
            })
        }
        catch (e: Exception) {
            currentCoroutineContext().ensureActive()
//            Toast.makeText(get().get(), e.message.toString(), Toast.LENGTH_SHORT).show()
            Log.d("RemoteServerRepo", e.message.toString())
            MyResult.Error(RemoteError.UNKNOWN_ERROR)
        }
    }
}