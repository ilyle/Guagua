package com.xiaoqi.guagua.retrofit

import com.xiaoqi.guagua.mvp.model.bean.EssayData
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface RetrofitService {

    @GET(Api.ESSAY_LIST + "{page}/json")
    fun getEssayData(@Path("page") page: Int): Observable<EssayData>

    @POST(Api.SEARCH_ESSAY + "{page}/json")
    fun searchEssayData(@Path("page") page: Int, @Query("k") query: String): Observable<EssayData>
}