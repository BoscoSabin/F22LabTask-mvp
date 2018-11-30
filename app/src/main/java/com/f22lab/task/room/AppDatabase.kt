package com.f22lab.task.room

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import com.f22lab.task.data.AppData

@Database(entities = arrayOf(AppData.FoodsData::class), version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): FoodsDao
}