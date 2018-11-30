package com.f22lab.task.ui

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import com.f22lab.task.R
import com.f22lab.task.adapter.FoodAdapter
import com.f22lab.task.constants.Constants
import com.f22lab.task.presenter.HomeActivityPresenter
import com.f22lab.task.data.AppData
import com.f22lab.task.dialog.DialogManager
import com.f22lab.task.util.CountDrawable
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), HomeActivityPresenter.MainPresenterView {

    override fun showFilterDialog() {
        mDialog.showFilterDialog()
    }

    override fun setFoodAdapter(foodAdapter: FoodAdapter) {
        rvFoodList.adapter = foodAdapter
    }

    override fun navigateDetailScreen(food: AppData.FoodsData) {
        val intent = Intent(applicationContext, FoodDetailedActivity::class.java)
        intent.putExtra(Constants.FOODDETAILS, food)
        startActivity(intent)
    }

    override fun navigateToCart() {
        startActivity(Intent(applicationContext, CartActivity::class.java))
    }

    override fun showSnackBar(message: String) {
        updateMessage(message)
    }

    override fun onFoodItemResult(foodDataList: List<AppData.FoodsData>) {
        (rvFoodList.adapter as FoodAdapter).refreshQuantity(foodDataList)
    }

    override fun updateCartQuanity(quantity: String) {
        CountDrawable.updateCount(mMenu.findItem(R.id.action_cart), applicationContext, quantity)
    }

    override fun setProgressVisibility(visiblity: Int) {
        progressBar.visibility = visiblity

    }

    override fun sortListByPrice() {
        (rvFoodList.adapter as FoodAdapter).sortListByPrice()
    }

    override fun sortListByRating() {
        (rvFoodList.adapter as FoodAdapter).sortListByRating()
    }

    val TAG = "MainActivity"
    lateinit var mMainPresenter: HomeActivityPresenter
    lateinit var mDialog: DialogManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        setupRecyclerview()
        mMainPresenter = HomeActivityPresenter(this)
        mMainPresenter.getFoodListItems()
        mDialog = DialogManager(this, mMainPresenter)

    }

    private fun setupRecyclerview() {
        val layoutManager = LinearLayoutManager(applicationContext)
        rvFoodList.layoutManager = layoutManager
    }

    private lateinit var mMenu: Menu

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        mMenu = menu
        val inflater = menuInflater
        inflater.inflate(R.menu.home_menu, menu)
        mMainPresenter.setCartItemsCount()
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle presses on the action bar menu items
        when (item.itemId) {
            R.id.action_filter -> {
                mMainPresenter.reqToShowFilterDialog()
                return true
            }
            R.id.action_cart -> {
                mMainPresenter.checkCartData()
                return true
            }

        }
        return super.onOptionsItemSelected(item)
    }

    override fun onResume() {
        super.onResume()
        checkCartQuanity()
    }

    private fun checkCartQuanity() {
        mMainPresenter.updateCartItemQuantity()
        if (::mMenu.isInitialized)
            mMainPresenter.setCartItemsCount()
    }


    fun updateMessage(message: String) {
        val snackbar = Snackbar.make(main_content, message, Snackbar.LENGTH_LONG)
        snackbar.show()
    }

//    fun setCartItemsCount() {
//        if (::mMenu.isInitialized){
//            val menuItem = mMenu.findItem(R.id.action_cart)
//
//        }
//    }


}
