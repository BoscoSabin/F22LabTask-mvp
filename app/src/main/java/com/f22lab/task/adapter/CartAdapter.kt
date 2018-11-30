package com.f22lab.task.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.f22lab.task.AppApplication
import com.f22lab.task.R
import com.f22lab.task.interfaces.AdapterItemClickListener
import com.f22lab.task.data.AppData
import kotlinx.android.synthetic.main.cart_list_items.view.*
import kotlinx.android.synthetic.main.food_list_item.view.*

class CartAdapter(val mAdapterItemClickListener: AdapterItemClickListener) : RecyclerView.Adapter<CartAdapter.ViewHolder>() {
    val mContext= AppApplication.instance.applicationContext
    val mFoodData = ArrayList<AppData.FoodsData>()
    override fun onCreateViewHolder(viewGroup: ViewGroup, pos: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.cart_list_items, viewGroup, false))

    }


    override fun getItemCount(): Int {
        return mFoodData.size
    }

    override fun onBindViewHolder(viewHoler: ViewHolder, pos: Int) {
        viewHoler.bindItems()
    }

    inner class ViewHolder(val mItemView: View) : RecyclerView.ViewHolder(mItemView)/*, View.OnClickListener*/ {


//        init {
//            mItemView.ivAddItem.setOnClickListener(this)
//            mItemView.ivRomoveItem.setOnClickListener(this)
//            mItemView.setOnClickListener(this)
//        }

//        override fun onClick(view: View?) {
//            val item = mFoodData.get(adapterPosition)
//            when (view?.id) {
//                R.id.ivAddItem ->mAdapterItemClickListener.onItemClick(view, adapterPosition, item, "AddItemToCart")
//                R.id.ivRomoveItem -> mAdapterItemClickListener.onItemClick(view, adapterPosition, item, "RemoveItemFromCart")
//                else ->mAdapterItemClickListener.onItemClick(view!!, adapterPosition, item, "Details")
//            }
//
//
//        }

        fun bindItems() {

            val item = mFoodData.get(adapterPosition)
            mItemView.tvCartFoodName.text = if (item.quantity > 1) mContext.getString(R.string.item_quantity, item.itemName, item.quantity) else item.itemName
            mItemView.tvCartFoodPrice.text = mContext.getString(R.string.price_format, item.itemPrice * item.quantity);
        }

    }

    fun updateAdapter(foodData: List<AppData.FoodsData>) {
        mFoodData.clear()
        mFoodData.addAll(foodData)
        notifyDataSetChanged()

    }


}