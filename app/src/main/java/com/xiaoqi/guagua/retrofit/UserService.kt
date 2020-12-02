package com.xiaoqi.guagua.retrofit


import com.xiaoqi.guagua.mvp.model.bean.UserData
import io.reactivex.Observable
import okhttp3.MultipartBody
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Query

/**
 * Created by xujie on 2018/12/10.
 * Mail : 617314917@qq.com
 */
interface UserService {

    /**
     * 使用用户名和密码登录
     */
    @POST(Api.LOGIN)
    fun login(@Query("username") username: String, @Query("password") password: String): Observable<UserData>

    /**
     * 使用token（服务器返回）登录
     */
    @POST(Api.LOGIN_TOKEN)
    fun login(@Query("token") token: String): Observable<UserData>

    /**
     * 退出
     */
    @POST(Api.LOGOUT)
    fun logout(@Query("uid") id: String): Observable<UserData>

    /**
     * 注册
     */
    @POST(Api.REGISTER)
    fun register(@Query("username") username: String, @Query("password") password: String): Observable<UserData>

    /**
     * 更新头像
     */
    @Multipart
    @POST(Api.UPDATE_AVATAR)
    fun updateAvatar(@Query("uid") uid: String, @Query("username") username: String, @Part avatar: MultipartBody.Part): Observable<UserData>
}