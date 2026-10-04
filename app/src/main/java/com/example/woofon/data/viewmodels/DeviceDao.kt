package com.example.woofon.data.viewmodels

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.woofon.data.viewmodels.models.DeviceModel
import kotlinx.coroutines.flow.Flow

@Dao
interface DeviceDao {
    @Query("SELECT * FROM devices")
    fun getDevices(): Flow<List<DeviceModel>>

    @Insert
    suspend fun addDevice(device: DeviceModel)

    @Update
    suspend fun updateDevice(device: DeviceModel)

    @Query("DELETE FROM devices WHERE id IN (:idDevice)")
    suspend fun deleteDevice(idDevice: List<Int>)
}
