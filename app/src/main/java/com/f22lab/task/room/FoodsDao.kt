package com.f22lab.task.room

import android.arch.persistence.room.*
import com.f22lab.task.data.AppData

@Dao
interface FoodsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(vararg food: AppData.FoodsData)

    @Query("DELETE FROM FoodsData WHERE itemName = :foodName")
    fun delete(foodName: String)

    @Query("SELECT * FROM FoodsData")
    fun getAll(): List<AppData.FoodsData>
   

}