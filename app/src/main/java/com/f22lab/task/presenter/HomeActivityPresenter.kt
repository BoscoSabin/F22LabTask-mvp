package com.f22lab.task.presenter

import android.app.Dialog
import android.util.Log
import com.f22lab.task.data.AppData
import com.f22lab.task.rest.ApiController
import android.view.View
import com.f22lab.task.adapter.FoodAdapter
import com.f22lab.task.dialog.DialogManager
import com.f22lab.task.interfaces.AdapterItemClickListener
import com.f22lab.task.room.RoomController


class HomeActivityPresenter(val mMainView: MainPresenterView) : ApiController.ApiCallBack, DialogManager.DialogListener, AdapterItemClickListener {


    interface MainPresenterView {
        fun onFoodItemResult(foodDataList: List<AppData.FoodsData>)
        fun sortListByPrice()
        fun sortListByRating()
        fun setProgressVisibility(visiblity: Int)
        fun showSnackBar(message: String)
        fun navigateToCart()
        fun navigateDetailScreen(food: AppData.FoodsData)
        fun setFoodAdapter(foodAdapter: FoodAdapter)
        fun showFilterDialog()
        fun updateCartQuanity(quantity: String)

    }

    private val TAG = "HomePresenter"
    private var mApiController = ApiController(this)
    private var mRoomController = RoomController()
    var mFoodAdapter = FoodAdapter(this)


    fun getAllItems(): List<AppData.FoodsData> {
        return mRoomController.getItems()
    }

    fun removeItemFromCart(food: AppData.FoodsData) {
        mRoomController.deleteItem(food)
        setCartItemsCount()

    }

    override fun onSuccess(type: Int, response: Any?) {
        mMainView.setProgressVisibility(View.GONE)
        mMainView.onFoodItemResult(response as List<AppData.FoodsData>)
        updateCartItemQuantity()
    }

    override fun onErrorResponse(type: Int, response: Any?) {
        mMainView.setProgressVisibility(View.GONE)
    }


    override fun onFailure(type: Int, response: Any) {
        mMainView.setProgressVisibility(View.GONE)
    }

    fun getFoodListItems() {
        mApiController.foodItemsApiCall(1)
    }

    fun reqToShowFilterDialog() {
        if (!mFoodAdapter.getListItem().isEmpty())
            mMainView.showFilterDialog()
    }

    fun addItemToCart(foodsData: AppData.FoodsData) {
        mRoomController.addItem(foodsData)
        setCartItemsCount()
    }

    override fun onDialogClick(requestCode: Int, dialog: Dialog, any: Any, type: String) {
        when (type) {
            "LowToHigh" -> mMainView.sortListByPrice()
            "Rating" -> mMainView.sortListByRating()
        }

    }

    fun updateCartItemQuantity() {
        val listItem = mFoodAdapter.getListItem()
        if (!listItem.isEmpty()) {
            listItem.map { it.quantity = 0 }
            val items = getAllItems()
            for (item in items) {
                listItem.find { it -> it.itemName == item.itemName }?.quantity = item.quantity
            }
            mMainView.onFoodItemResult(listItem)
        }
    }

    fun setCartItemsCount() {
        mMainView.updateCartQuanity(getAllItems().sumBy { it.quantity }.toString())
    }

    fun checkCartData() {
        if (getAllItems().isEmpty())
            mMainView.showSnackBar("No items in cart.")
        else mMainView.navigateToCart()

    }

    override fun onItemClick(v: View, position: Int, data: Any, requestType: String) {
        val food = data as AppData.FoodsData
        when (requestType) {
            "AddItemToCart" -> addItemToCart(food)
            "RemoveItemFromCart" -> removeItemFromCart(food)
            "Details" -> mMainView.navigateDetailScreen(food)
        }
    }

    init {
        mMainView.setFoodAdapter(mFoodAdapter)
    }


}