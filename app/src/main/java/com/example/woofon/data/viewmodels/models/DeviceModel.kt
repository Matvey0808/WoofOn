package com.example.woofon.data.viewmodels.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "devices")
data class DeviceModel(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "mac") val macAddress: String,
    @ColumnInfo(name = "broadcast") val broadcastAddress: String,
    @ColumnInfo(name = "port") val port: Int
)