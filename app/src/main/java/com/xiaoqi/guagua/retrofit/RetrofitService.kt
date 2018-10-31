package com.xiaoqi.guagua.retrofit

import com.xiaoqi.guagua.mvp.model.bean.*
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

    @GET(Api.CATEGORY_LIST)
    fun listCategory(): Observable<CategoryData>

    @GET(Api.ARTICLE_LIST + "{page}/json")
    fun categoryArticle(@Path("page") page: Int, @Query("cid") categoryId: Int): Observable<ArticleData>

    @GET(Api.BANNER)
    fun getBanner(): Observable<BannerData>

    @POST(Api.LOGIN)
    fun login(@Query("username") username: String, @Query("password") password: String): Observable<UserData>

    @POST(Api.LOGOUT)
    fun logout(@Query("id") id: Int): Observable<UserData>

    @POST(Api.REGISTER)
    fun register(@Query("username") username: String, @Query("password") password: String): Observable<UserData>
}