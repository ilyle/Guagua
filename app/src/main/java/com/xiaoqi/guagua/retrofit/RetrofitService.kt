package com.xiaoqi.guagua.retrofit

import com.xiaoqi.guagua.bean.Essay
import com.xiaoqi.guagua.data.EssayData
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path

interface RetrofitService {

    @GET(Api.ESSAY_LIST + "{page}/json")
    fun getEssay(@Path("page") page: Int): Observable<EssayData>;
}