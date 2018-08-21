package com.xiaoqi.guagua.retrofit

import com.xiaoqi.guagua.mvp.model.bean.ArticleData
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface RetrofitService {

    @GET(Api.ARTICLE_LIST + "{page}/json")
    fun listArticle(@Path("page") page: Int): Observable<ArticleData>

    @POST(Api.ARTICLE_QUERY + "{page}/json")
    fun queryArticle(@Path("page") page: Int, @Query("k") query: String): Observable<ArticleData>
}