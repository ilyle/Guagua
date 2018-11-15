package com.xiaoqi.guagua.mvp.model.source.repository

import com.xiaoqi.guagua.mvp.model.bean.Banner
import com.xiaoqi.guagua.mvp.model.source.BannerDataSource
import com.xiaoqi.guagua.mvp.model.source.remote.BannerDataSourceRemote
import io.reactivex.Observable

class BannerDataRepository(private val mRemote: BannerDataSourceRemote) : BannerDataSource {

    companion object {
        private var mInstance: BannerDataRepository? = null
        fun getInstance(remote: BannerDataSourceRemote): BannerDataRepository {
            if (mInstance == null) {
                mInstance = BannerDataRepository(remote)
            }
            return mInstance!!
        }
    }

    override fun getBanner(): Observable<List<Banner>> {
        return mRemote.getBanner()
    }
}