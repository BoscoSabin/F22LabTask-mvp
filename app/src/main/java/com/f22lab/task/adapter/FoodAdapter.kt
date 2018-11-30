package com.f22lab.task.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.f22lab.task.AppApplication
import com.f22lab.task.R
import com.f22lab.task.interfaces.AdapterItemClickListener
import com.f22lab.task.data.AppData
import kotlinx.android.synthetic.main.food_list_item.view.*

class FoodAdapter(val mAdapterItemClickListener: AdapterItemClickListener) : RecyclerView.Adapter<FoodAdapter.ViewHolder>() {

    val mContext=AppApplication.instance.applicationContext
    var mFoodData = ArrayList<AppData.FoodsData>()
    override fun onCreateViewHolder(viewGroup: ViewGroup, pos: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.food_list_item, viewGroup, false))

    }


    override fun getItemCount(): Int {
        return mFoodData.size
    }

    override fun onBindViewHolder(viewHoler: ViewHolder, pos: Int) {
        viewHoler.bindItems()
    }

    inner class ViewHolder(val mItemView: View) : RecyclerView.ViewHolder(mItemView), View.OnClickListener {


        init {
            mItemView.ivAddItem.setOnClickListener(this)
            mItemView.ivRomoveItem.setOnClickListener(this)
            mItemView.setOnClickListener(this)
        }

        override fun onClick(view: View?) {
            val item = mFoodData.get(adapterPosition)
            when (view?.id) {
                R.id.ivAddItem -> {
                    ++item.quantity
                    updateQuantity(item)
                    mAdapterItemClickListener.onItemClick(view, adapterPosition, item, "AddItemToCart")

                }
                R.id.ivRomoveItem -> {
                    item.quantity = if (item.quantity > 0) --item.quantity else 0
                    updateQuantity(item)
                    mAdapterItemClickListener.onItemClick(view, adapterPosition, item, "RemoveItemFromCart")

                }
                else -> mAdapterItemClickListener.onItemClick(view!!, adapterPosition, item, "Details")
            }


        }

        fun bindItems() {

            val item = mFoodData.get(adapterPosition)
            mItemView.tvFoodName.text = item.itemName
            mItemView.tvPrice.text = mContext.getString(R.string.food_price, item.itemPrice)
            mItemView.tvRating.text = mContext.getString(R.string.food_rating, item.averageRating)
            updateQuantity(item)
            Glide.with(mContext).load(item.imageUrl).apply(RequestOptions.placeholderOf(R.drawable.food_paceholder)).into(mItemView.ivFoodPic)
        }

        private fun updateQuantity(item: AppData.FoodsData) {
            mItemView.tvItemCount.text = if (item.quantity <= 0) "Add" else "${item.quantity}"
        }

    }

    fun addItems(foodData: List<AppData.FoodsData>) {
        mFoodData.clear()
        mFoodData.addAll(foodData)
        notifyDataSetChanged()

    }fun refreshQuantity(foodData: List<AppData.FoodsData>) {
//        mFoodData.clear()
        mFoodData= foodData as ArrayList<AppData.FoodsData>
        notifyDataSetChanged()

    }

    fun getListItem(): ArrayList<AppData.FoodsData> {
        return mFoodData
    }

    fun sortListByPrice() {
        mFoodData.sortBy { it.itemPrice }
        notifyDataSetChanged()
    }

    fun sortListByRating() {
        mFoodData.sortByDescending { it.averageRating }
        notifyDataSetChanged()
    }
}