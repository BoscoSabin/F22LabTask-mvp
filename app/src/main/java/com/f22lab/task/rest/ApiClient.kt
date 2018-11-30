package com.f22lab.task.rest

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class ApiClient {


    companion object {
        val BASEURL = "https://android-full-time-task.firebaseio.com/"
        var mApiInterface: ApiInterface?=null
        fun getApiInterface(): ApiInterface {
            val gson = GsonBuilder().setLenient().create()

            val httpClient = OkHttpClient.Builder().connectTimeout(60, TimeUnit.SECONDS).readTimeout(100, TimeUnit.SECONDS).addInterceptor { chain ->
                val newRequest: Request
                newRequest = chain.request().newBuilder()
                        .addHeader("Accept", "application/json")
                        .addHeader("Accept", "application/x-www-form-urlencoded")
                        .build()

                chain.proceed(newRequest)
            }.build()
            if (mApiInterface ==null) {
                val retrofit = Retrofit.Builder()
                        .baseUrl(BASEURL)
                        .client(httpClient)
                        .addConverterFactory(GsonConverterFactory.create(gson))
                        .build()

                mApiInterface = retrofit.create(ApiInterface::class.java)
            }
            return mApiInterface!!
        }


    }
}