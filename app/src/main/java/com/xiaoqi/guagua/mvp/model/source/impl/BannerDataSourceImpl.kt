package com.xiaoqi.guagua.mvp.model.source.impl

import com.xiaoqi.guagua.mvp.model.bean.Banner
import com.xiaoqi.guagua.mvp.model.source.BannerDataSource
import com.xiaoqi.guagua.mvp.model.source.remote.BannerDataSourceRemote
import io.reactivex.Observable

class BannerDataSourceImpl(private val mRemote: BannerDataSourceRemote) : BannerDataSource {

    companion object {
        private var mInstance: BannerDataSourceImpl? = null
        fun getInstance(remote: BannerDataSourceRemote): BannerDataSourceImpl {
            if (mInstance == null) {
                mInstance = BannerDataSourceImpl(remote)
            }
            return mInstance!!
        }
    }

    override fun getBanner(): Observable<List<Banner>> {
        return mRemote.getBanner()
    }
}