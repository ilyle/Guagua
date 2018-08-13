package com.xiaoqi.guagua.mvp.model.source.remote

import com.xiaoqi.guagua.mvp.model.bean.EssayData
import com.xiaoqi.guagua.mvp.model.source.EssayDataSource
import com.xiaoqi.guagua.retrofit.RetrofitClient
import com.xiaoqi.guagua.retrofit.RetrofitService
import com.xiaoqi.guagua.util.SortDescendUtil
import io.reactivex.Observable

/**
 * 文章数据类，远程获取
 */
class EssayDataSourceRemote private constructor(): EssayDataSource {

    companion object {
        private var singleInstance : EssayDataSourceRemote? = null

        fun getInstance() : EssayDataSourceRemote {
            if (singleInstance == null) {
                singleInstance = EssayDataSourceRemote()
            }
            return singleInstance!!
        }
    }

    override fun getEssay(page: Int, forceUpdate: Boolean, clearCache: Boolean): Observable<MutableList<EssayData.Data.Essay>> {
        return RetrofitClient.getInstance()
                .create(RetrofitService::class.java)
                .getEssayData(page)
                .filter { it.errorCode != -1 }
                .flatMap { Observable.fromIterable(it.data?.datas).toSortedList { e1, e2 -> SortDescendUtil.sortEssay(e1, e2) }.toObservable() }
    }

}