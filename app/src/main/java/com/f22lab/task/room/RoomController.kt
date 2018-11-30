package com.f22lab.task.room

import com.f22lab.task.AppApplication
import com.f22lab.task.data.AppData
import com.f22lab.task.interfaces.IRoomController

class RoomController : IRoomController {
    private val mDatabase = AppApplication.instance.databaseInstance

    override fun addItem(foodsData: AppData.FoodsData) {
        mDatabase.userDao().insert(foodsData)
    }

    override fun getItems(): List<AppData.FoodsData> {
        return mDatabase.userDao().getAll()
    }

    override fun removeItem(food: AppData.FoodsData) {
        mDatabase.userDao().delete(food.itemName)
    }

    override fun deleteItem(food: AppData.FoodsData) {

        if (food.quantity > 0) {
            addItem(food)
        } else removeItem(food)

    }

}
