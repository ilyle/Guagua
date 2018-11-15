package com.xiaoqi.guagua.mvp.model.source.repository

import io.reactivex.Observable
import com.xiaoqi.guagua.mvp.model.bean.UserData
import com.xiaoqi.guagua.mvp.model.source.UserDataSource
import com.xiaoqi.guagua.mvp.model.source.remote.UserDataSourceRemote

class UserDataRepository(private val mRemote: UserDataSourceRemote) : UserDataSource {

    companion object {
        private var mInstance: UserDataRepository? = null
        fun getInstance(remote: UserDataSourceRemote): UserDataRepository {
            if (mInstance == null) {
                mInstance = UserDataRepository(remote)
            }
            return mInstance!!
        }
    }

    override fun login(username: String, password: String): Observable<UserData> {
        return mRemote.login(username, password)
    }

    override fun login(token: String): Observable<UserData> {
        return mRemote.login(token)
    }

    override fun logout(uid: String): Observable<UserData> {
        return mRemote.logout(uid)
    }

    override fun register(username: String, password: String): Observable<UserData> {
        return mRemote.register(username, password)
    }
}