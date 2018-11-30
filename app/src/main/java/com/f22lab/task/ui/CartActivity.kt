package com.f22lab.task.ui

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.f22lab.task.adapter.CartAdapter
import com.f22lab.task.data.AppData
import com.f22lab.task.dialog.DialogManager
import kotlinx.android.synthetic.main.activity_cart.*
import android.support.design.widget.Snackbar
import com.f22lab.task.R
import com.f22lab.task.presenter.CartPresenter


class CartActivity : AppCompatActivity(), CartPresenter.ICart {
    override fun updateAmount(total: Double, deliveryCharge: Double, grand: Double) {
        tvTotalAmount.text =  getString(R.string.price_format, total)
        tvDeliveryCharge.text = getString(R.string.price_format, deliveryCharge)
        tvGrandTotal.text = getString(R.string.price_format, grand)
    }

    override fun showDiscount() {
        tvDiscount.visibility = View.VISIBLE
    }

    override fun hideDiscount() {
        tvDiscount.visibility = View.GONE
    }

    override fun onErrorMessage(message: String) {
        showSnackBar(message)
    }

    override fun onCoupenApplied(message: String) {
        showDiscount()
        tvDiscount.text=message
    }

    override fun showCouponDialog() {
        mDialog.applyCouponDialog()

    }


    override fun setAdapter(cartAdapter: CartAdapter, carditem: List<AppData.FoodsData>) {
        mCartItem = carditem
        rvCartItem.adapter = cartAdapter
        cartAdapter.updateAdapter(carditem)
    }


    val TAG = "CartActivity"
    private lateinit var mDialog: DialogManager
    private lateinit var mPresenter: CartPresenter

    private lateinit var mCartItem: List<AppData.FoodsData>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)
        setupActionBar()
        setupRecyclerview()
        mPresenter = CartPresenter(this)
        mDialog = DialogManager(this, mPresenter)


    }

    private fun setupActionBar() {
        setSupportActionBar(toolbarCart)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        setTitle("Your Cart")
    }


    private fun setupRecyclerview() {
        val layoutManager = LinearLayoutManager(applicationContext)
        rvCartItem.layoutManager = layoutManager
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.offer_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_coupon -> {
                mPresenter.reqToCoupenDialog()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    fun showSnackBar(message: String) {
        val snackbar = Snackbar.make(llCartRootView, message, Snackbar.LENGTH_LONG)
        snackbar.show()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}