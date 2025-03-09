package com.subhranil.bluemoon.lite.repository.testing

import com.subhranil.bluemoon.lite.models.BasicInfo
import com.subhranil.bluemoon.lite.models.DiskType
import com.subhranil.bluemoon.lite.models.Drive
import com.subhranil.bluemoon.lite.models.EntryType
import com.subhranil.bluemoon.lite.models.FsEntry
import com.subhranil.bluemoon.lite.repository.ServerRepository
import com.subhranil.bluemoon.lite.utils.result.MyResult
import com.subhranil.bluemoon.lite.utils.result.RemoteError
import kotlinx.coroutines.delay

class TestingServerRepo: ServerRepository {
    override suspend fun isServerReachable(
        url: String
    ): Boolean {
        delay(50)
        return true
    }

    override suspend fun getBasicInfo(
        url: String
    ): MyResult<BasicInfo, RemoteError> {
        delay(1000)
        return MyResult.Success(BasicInfo("Test Host", "Windows", "1.0", "1.0", url, ))
//        return MyResult.Error(RemoteError.NETWORK_ERROR)
    }

    override suspend fun getDrives(
        url: String
    ): MyResult<List<Drive>, RemoteError> {
        delay(50)
        val drives = listOf(
            Drive(
                "C Drive Name",
                50000000,
                1000000000,
                DiskType.SSD,
                "C:\\",
                "NTFS"
            ),
            Drive(
                "D Drive Name",
                1250000000,
                2000000000,
                DiskType.HDD,
                "D:\\",
                "NTFS"
            )
        )
        return MyResult.Success(drives)
    }

    override suspend fun getEntries(
        url: String,
        path: String
    ): MyResult<List<FsEntry>, RemoteError> {
        delay(50)
        val entries = listOf(
            FsEntry(
                "file1.txt",
                1000,
                EntryType.FILE,
                null,
                "D:\\file1.txt"
            ),
            FsEntry(
                "file2.txt",
                2000,
                EntryType.FILE,
                null,
                "D:\\file2.txt"
            ),
            FsEntry(
                "dir1",
                0,
                EntryType.DIRECTORY,
                null,
                "D:\\dir1"
            ),
            FsEntry(
                "dir2",
                0,
                EntryType.DIRECTORY,
                null,
                "D:\\dir2"
            )
        )
        return MyResult.Success(entries)
    }
}