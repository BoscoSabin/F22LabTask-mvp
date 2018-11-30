package com.f22lab.task.rest

import com.f22lab.task.data.AppData
import retrofit2.Call
import retrofit2.http.GET

interface ApiInterface {

    @GET("data.json")
    fun getFoodItems(): Call<List<AppData.FoodsData>>
}