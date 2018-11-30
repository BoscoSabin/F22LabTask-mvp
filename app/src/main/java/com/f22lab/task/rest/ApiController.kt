package com.f22lab.task.rest

import com.f22lab.task.data.AppData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ApiController(val mApiCallBack: ApiCallBack) {
    private var mApiInterface: ApiInterface = ApiClient.getApiInterface()

    interface ApiCallBack {
        fun onSuccess(type: Int, response: Any?)

        fun onErrorResponse(type: Int, response: Any?)

        fun onFailure(type: Int, response: Any)
    }

    fun foodItemsApiCall(requestCode: Int) {

        val mLoginApiCall = mApiInterface.getFoodItems()
        mLoginApiCall.enqueue(object : Callback<List<AppData.FoodsData>> {
            override fun onResponse(call: Call<List<AppData.FoodsData>>, response: Response<List<AppData.FoodsData>>) {
                if (response.isSuccessful() && response.body() != null) {
                    mApiCallBack.onSuccess(requestCode, response.body())
                } else {
                    mApiCallBack.onErrorResponse(requestCode, response.body())
                }
            }

            override fun onFailure(call: Call<List<AppData.FoodsData>>, t: Throwable) {

                mApiCallBack.onFailure(requestCode, t)
            }
        })
    }
}