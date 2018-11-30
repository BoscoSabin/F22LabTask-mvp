package com.f22lab.task.dialog

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.support.v7.app.AlertDialog
import android.view.Window
import android.view.WindowManager
import android.widget.EditText
import com.f22lab.task.R
import kotlinx.android.synthetic.main.coupon_dialog.*


/**
 * Created by ZCODIA-PC on 12-07-2018.
 */

class DialogManager(private val mContext: Context, private val mDialogListener: DialogListener) {

    interface DialogListener {
        fun onDialogClick(requestCode: Int, dialog: Dialog, any: Any, type: String)
    }


    fun showFilterDialog() {

        var dialog1: AlertDialog? = null
        val builder = AlertDialog.Builder(mContext)
        builder.setTitle("Filter")
        val animals = arrayOf("Price Low to High", "Rating")
        builder.setItems(animals, { dialog, which ->
            when (which) {
                0 -> {
                    mDialogListener.onDialogClick(1, dialog1!!, "Price Low to High", "LowToHigh")
                }
                1 -> {
                    mDialogListener.onDialogClick(1, dialog1!!, "Rating", "Rating")
                }

            }
        })

        dialog1 = builder.create()
        dialog1.show()
    }

    fun applyCouponDialog() {
        val dialog = Dialog(mContext)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.coupon_dialog)
        val window = dialog.window
        window!!.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT)

        val edtEmail = dialog.findViewById<EditText>(R.id.edtCoupon)

        dialog.btnApplyCoupon.setOnClickListener {
            dialog.dismiss()
            mDialogListener.onDialogClick(0, dialog, edtEmail.text.toString(), "ApplyCoupon")
        }



        dialog.show()

    }

}
