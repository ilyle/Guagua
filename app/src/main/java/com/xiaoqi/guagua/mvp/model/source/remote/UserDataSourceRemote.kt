package com.xiaoqi.guagua.mvp.model.source.remote

import com.xiaoqi.guagua.mvp.model.bean.UserData
import com.xiaoqi.guagua.mvp.model.source.UserDataSource
import com.xiaoqi.guagua.retrofit.RetrofitClient
import com.xiaoqi.guagua.retrofit.UserService
import io.reactivex.Observable
import okhttp3.MultipartBody

class UserDataSourceRemote : UserDataSource {

    companion object {
        private var mInstance: UserDataSourceRemote? = null

        fun getInstance(): UserDataSourceRemote {
            if (mInstance == null) {
                mInstance = UserDataSourceRemote()
            }
            return mInstance!!
        }
    }

    override fun login(username: String, password: String): Observable<UserData> {
        return RetrofitClient.getGuaGuaInstance()
                .create(UserService::class.java)
                .login(username, password)
    }

    override fun login(token: String): Observable<UserData> {
        return RetrofitClient.getGuaGuaInstance()
                .create(UserService::class.java)
                .login(token)
    }

    override fun logout(uid: String): Observable<UserData> {
        return RetrofitClient.getGuaGuaInstance()
                .create(UserService::class.java)
                .logout(uid)
    }

    override fun register(username: String, password: String): Observable<UserData> {
        return RetrofitClient.getGuaGuaInstance()
                .create(UserService::class.java)
                .register(username, password)
    }

    override fun updateAvatar(uid: String, username: String, avatar: MultipartBody.Part): Observable<UserData> {
        return RetrofitClient.getGuaGuaInstance()
                .create(UserService::class.java)
                .updateAvatar(uid, username, avatar)
    }
}