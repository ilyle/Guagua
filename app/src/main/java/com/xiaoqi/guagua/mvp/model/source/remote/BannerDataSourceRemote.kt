package com.xiaoqi.guagua.mvp.model.source.remote

import com.xiaoqi.guagua.mvp.model.bean.Banner
import com.xiaoqi.guagua.mvp.model.source.BannerDataSource
import com.xiaoqi.guagua.retrofit.RetrofitClient
import com.xiaoqi.guagua.retrofit.RetrofitService
import io.reactivex.Observable

class BannerDataSourceRemote : BannerDataSource {

    companion object {
        private var mInstance: BannerDataSourceRemote? = null

        fun getInstance(): BannerDataSourceRemote {
            if (mInstance == null) {
                mInstance = BannerDataSourceRemote()
            }
            return mInstance!!
        }
    }

    override fun getBanner(): Observable<List<Banner>> {
        return RetrofitClient.getWanAndroidInstance()
                .create(RetrofitService::class.java)
                .getBanner()
                .filter { it.errorCode != -1 }
                .flatMap { Observable.fromIterable(it.data).toList().toObservable() }
    }
}