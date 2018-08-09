package com.xiaoqi.guagua.data.source.remote

import com.xiaoqi.guagua.bean.Essay
import com.xiaoqi.guagua.data.source.EssayDataSource
import com.xiaoqi.guagua.retrofit.RetrofitClient
import com.xiaoqi.guagua.retrofit.RetrofitService
import io.reactivex.Observable

class EssayDataRemoteSource : EssayDataSource {

    companion object {
        val instance by lazy { EssayDataRemoteSource() }
    }

    override fun getEssay(page: Int): Observable<List<Essay>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}