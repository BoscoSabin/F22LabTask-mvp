package com.f22lab.task.data

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

class AppData {
    @Entity
    data class FoodsData(
            @Expose
            @SerializedName("average_rating")
            val averageRating: Double,
            @Expose
            @SerializedName("image_url")
            val imageUrl: String,
            @PrimaryKey
            @Expose
            @SerializedName("item_name")
            val itemName: String,
            @Expose
            @SerializedName("item_price")
            var itemPrice: Double,
            var quantity: Int=0

    ):Serializable
}