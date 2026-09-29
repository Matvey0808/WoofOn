package com.example.woofon.data.viewmodels

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.woofon.data.viewmodels.models.DeviceModel

@Database(
    version = 1,
    entities = [(DeviceModel::class)]
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun getDeviceDao() : DeviceDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null
        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "devices"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}