package com.xiaoqi.guagua.mvp.model.source

import com.xiaoqi.guagua.mvp.model.bean.Banner
import io.reactivex.Observable

interface BannerDataSource {
    fun getBanner(): Observable<List<Banner>>
}