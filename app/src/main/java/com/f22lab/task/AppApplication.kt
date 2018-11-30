package com.f22lab.task

import android.app.Application
import android.arch.persistence.room.Room
import com.f22lab.task.constants.Constants
import com.f22lab.task.room.AppDatabase

class AppApplication : Application() {
    lateinit var databaseInstance: AppDatabase

    override fun onCreate() {
        super.onCreate()
        instance = this
        databaseInstance = Room.databaseBuilder(applicationContext, AppDatabase::class.java, Constants.DATABASENAME).allowMainThreadQueries().build()
    }

    companion object {
        lateinit var instance: AppApplication

    }



}