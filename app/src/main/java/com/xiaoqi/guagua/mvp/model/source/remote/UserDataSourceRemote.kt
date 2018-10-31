package com.xiaoqi.guagua.mvp.model.source.remote

import io.reactivex.Observable
import com.xiaoqi.guagua.mvp.model.bean.User
import com.xiaoqi.guagua.mvp.model.bean.UserData
import com.xiaoqi.guagua.mvp.model.source.UserDataSource
import com.xiaoqi.guagua.retrofit.RetrofitClient
import com.xiaoqi.guagua.retrofit.RetrofitService

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
                .create(RetrofitService::class.java)
                .login(username, password)
    }

    override fun logout(userId: Int): Observable<UserData> {
        return RetrofitClient.getGuaGuaInstance()
                .create(RetrofitService::class.java)
                .logout(userId)
    }

    override fun register(username: String, password: String): Observable<UserData> {
        return RetrofitClient.getGuaGuaInstance()
                .create(RetrofitService::class.java)
                .register(username, password)
    }
}