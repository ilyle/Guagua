package com.xiaoqi.guagua.retrofit

import com.xiaoqi.guagua.mvp.model.bean.EssayData
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path

interface RetrofitService {

    @GET(Api.ESSAY_LIST + "{page}/json")
    fun getEssayData(@Path("page") page: Int): Observable<EssayData>;
}