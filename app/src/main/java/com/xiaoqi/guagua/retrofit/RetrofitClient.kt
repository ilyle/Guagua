package com.xiaoqi.guagua.retrofit

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {

    private var okHttpClient: OkHttpClient = OkHttpClient.Builder().build()

    private var wanAndroidRetrofit: Retrofit = Retrofit.Builder()
            .baseUrl(Api.API_WAN_ANDROID)
            .client(okHttpClient)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    private var guaGuaRetrofit: Retrofit = Retrofit.Builder()
            .baseUrl(Api.API_GUA_GUA)
            .client(okHttpClient)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    fun getWanAndroidInstance(): Retrofit {
        return wanAndroidRetrofit
    }

    fun getGuaGuaInstance(): Retrofit {
        return guaGuaRetrofit
    }

}