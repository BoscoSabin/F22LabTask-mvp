package com.f22lab.task.ui

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.f22lab.task.R
import com.f22lab.task.constants.Constants
import com.f22lab.task.data.AppData
import com.f22lab.task.presenter.FoodDetailedPresenter
import kotlinx.android.synthetic.main.activity_food_details.*

class FoodDetailedActivity : AppCompatActivity(), View.OnClickListener, FoodDetailedPresenter.IFoodDetails {
    override fun updateItemQuantity(foodData: AppData.FoodsData) {
        mFoodData = foodData
        updateQuantity()
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.ivRemoveItem -> {
                mPresenter.addItemToCart(mFoodData)
            }
            R.id.ivAddItem -> {
                mPresenter.removeItemFromCart(mFoodData)

            }
        }
    }

    val TAG = "FoodDetailedActivity"
    private lateinit var mFoodData: AppData.FoodsData
    private lateinit var mPresenter: FoodDetailedPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_food_details)
        setupActionBar()


        intent?.extras?.let {
            mFoodData = intent.getSerializableExtra(Constants.FOODDETAILS) as AppData.FoodsData
            updateItems()
        }
        mPresenter = FoodDetailedPresenter(this)
    }

    private fun setupActionBar() {
        setSupportActionBar(toolbarFoodDetails)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        setTitle("Food Details")
    }

    fun updateItems() {
        ivRemoveItem.setOnClickListener(this)
        ivAddItem.setOnClickListener(this)
        tvFoodName.text = mFoodData.itemName
        tvPrice.text = getString(R.string.food_price, mFoodData.itemPrice)
        tvRating.text = getString(R.string.food_rating, mFoodData.averageRating)
        updateQuantity()
        Glide.with(applicationContext).load(mFoodData.imageUrl).apply(RequestOptions.placeholderOf(R.drawable.food_paceholder)).into(ivFoodPic)
    }

    private fun updateQuantity() {
        tvItemCount.text = if (mFoodData.quantity <= 0) "Add" else "${mFoodData.quantity}"
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}