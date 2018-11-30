package com.f22lab.task.presenter

import android.app.Dialog
import android.view.View
import com.f22lab.task.adapter.CartAdapter
import com.f22lab.task.data.AppData
import com.f22lab.task.dialog.DialogManager
import com.f22lab.task.interfaces.AdapterItemClickListener
import com.f22lab.task.room.RoomController

class CartPresenter(val mICart: ICart) : AdapterItemClickListener, DialogManager.DialogListener {
    override fun onDialogClick(requestCode: Int, dialog: Dialog, any: Any, type: String) {
        val couponCode = (any as String).trim { it <= ' ' }
        calculateGrandAmount(couponCode)
    }

    override fun onItemClick(v: View, position: Int, data: Any, requestType: String) {
    }

    fun reqToCoupenDialog() {
        if (!mCartItemList.isEmpty()) {
            mICart.showCouponDialog()
        }

    }

    fun calculateGrandAmount(couponCode: String) {
        var total = mCartItemList.sumByDouble { it.itemPrice * it.quantity }
        total = applyCouponCode(couponCode, total)
        mICart.updateAmount(total, deliveryCharge, (total + deliveryCharge))
    }

    interface ICart {

        fun setAdapter(cartAdapter: CartAdapter, carditem: List<AppData.FoodsData>)
        fun showCouponDialog()
        fun updateAmount(total: Double, deliveryCharge: Double, grand: Double)
        fun showDiscount()
        fun hideDiscount()
        fun onErrorMessage(message: String)
        fun onCoupenApplied(message: String)

    }

    var mCartAdapter = CartAdapter(this)
    private val mRoomController = RoomController()
    private var mCartItemList: List<AppData.FoodsData>

    init {
        mCartItemList = mRoomController.getItems()
        mICart.setAdapter(mCartAdapter, mCartItemList)
        calculateGrandAmount("")
    }


    private var deliveryCharge: Double = 30.0

    private fun applyCouponCode(couponCode: String, totalAmount: Double): Double {
        mICart.hideDiscount()
        deliveryCharge = 30.0
        when (couponCode) {
            "F22LABS" -> {
                if (totalAmount > 400) {
                    mICart.onCoupenApplied("‘F22LABS’ coupon code is applied. Now you got 20% discount from total amount.")
                    return totalAmount - totalAmount * 20 / 100
                } else mICart.onErrorMessage("This coupon code is only valid if total ordered amount is greater than RS.400.00.")

            }
            "FREEDEL" -> {
                if (totalAmount > 100) {
                    deliveryCharge = 0.0
                    mICart.onCoupenApplied("‘FREEDEL’ coupon code is applied. Free Delivery charge.")
                } else mICart.onErrorMessage("Free delivery coupon code is only valid if total ordered amount is greater than RS.100.00.")

            }
            else -> {
                if (!couponCode.isEmpty())
                    mICart.onErrorMessage("Invalid Coupon Code.")

            }
        }

        return totalAmount
    }

}