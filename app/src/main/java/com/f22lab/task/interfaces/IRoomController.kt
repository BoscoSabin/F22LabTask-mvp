package com.f22lab.task.interfaces

import com.f22lab.task.data.AppData

interface IRoomController{
    fun addItem(foodsData: AppData.FoodsData)
    fun getItems():List<AppData.FoodsData>
    fun removeItem(food: AppData.FoodsData)
    fun deleteItem(food: AppData.FoodsData)
}
