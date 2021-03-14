package com.helloqzh.android.logic.dao

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.helloqzh.android.SunnyWeatherApplication
import com.helloqzh.android.logic.model.City

@Database(version = 1, entities = [City::class])
abstract class AppDatabase : RoomDatabase() {

    abstract fun cityDao(): CityDao

    companion object {

        private var instance: AppDatabase? = null
        const val WORLD_CITY_DB = "databases/worldcities.db"
        const val DB_NAME = "app_database.db"

        @Synchronized
        fun getDatabase(): AppDatabase {
            instance?.let {
                return it
            }
            return Room.databaseBuilder(SunnyWeatherApplication.context,
                AppDatabase::class.java, DB_NAME)
                .createFromAsset(WORLD_CITY_DB)
                .build().apply {
                    instance = this
                }
        }
    }
}