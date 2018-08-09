package com.xiaoqi.guagua.retrofit

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {

    private var okHttpClient: OkHttpClient = OkHttpClient.Builder().build()

    private var retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(Api.API_BASE)
            .client(okHttpClient)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    fun getInstance(): Retrofit {
        return retrofit
    }

}