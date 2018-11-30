package com.f22lab.task.presenter

import com.f22lab.task.data.AppData
import com.f22lab.task.room.RoomController

class FoodDetailedPresenter(val IMain: IFoodDetails) {
    interface IFoodDetails {

        fun updateItemQuantity(foodData: AppData.FoodsData)

    }

    private var mRoomController = RoomController()


    fun addItemToCart(foodData: AppData.FoodsData) {
        foodData.quantity = if (foodData.quantity > 0) --foodData.quantity else 0
        mRoomController.deleteItem(foodData)
        IMain.updateItemQuantity(foodData)
    }

    fun removeItemFromCart(foodData: AppData.FoodsData) {
        ++foodData.quantity
        mRoomController.addItem(foodData)
        IMain.updateItemQuantity(foodData)

    }


}