package com.subhranil.bluemoon.lite.repository.actual

import android.content.Context
import android.util.Log
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringSetPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.subhranil.bluemoon.lite.models.BasicInfo
import com.subhranil.bluemoon.lite.models.local.JustBasicInfo
import com.subhranil.bluemoon.lite.repository.LocalInfoRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

class LocalDataStore(
    private val context: Context
) : LocalInfoRepository {
    private val Context.datastore by preferencesDataStore("local_data")

    companion object {
        val previousConnectionsKey = stringSetPreferencesKey("previous_connections")
    }

    override suspend fun getAllPreviousBasicInfo(): List<JustBasicInfo> {
        val previousConnections: MutableList<JustBasicInfo> = mutableListOf()

        val preferences = context.datastore.data.first() // Collects the first emitted value

        Log.d("LocalDataStore", "Getting previous connections: $preferences")

        preferences[previousConnectionsKey]?.forEach { data ->
            try {
                previousConnections.add(Json.decodeFromString(data))
            } catch (e: Exception) {
                Log.e("LocalDataStore", "Failed to decode JSON", e)
            }
        }

        Log.d("LocalDataStore", "Returning previous connections: $previousConnections")
        return previousConnections
    }

    override suspend fun addAllBasicInfo(basicInfos: List<JustBasicInfo>) {
        Log.d("LocalDataStore", "Adding basic infos: $basicInfos")
        context.datastore.edit { preferences ->
            val previousConnections: MutableSet<String> = mutableSetOf()
            basicInfos.forEach { basicInfo ->
                previousConnections.add(Json.encodeToString(basicInfo))
            }
            preferences[previousConnectionsKey] = previousConnections

        }
    }

}
