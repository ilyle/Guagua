package com.xiaoqi.guagua.mvp.model.source.impl

import io.reactivex.Observable
import com.xiaoqi.guagua.mvp.model.bean.User
import com.xiaoqi.guagua.mvp.model.bean.UserData
import com.xiaoqi.guagua.mvp.model.source.UserDataSource
import com.xiaoqi.guagua.mvp.model.source.remote.UserDataSourceRemote

class UserDataSourceImpl(private val mRemote: UserDataSourceRemote) : UserDataSource {

    companion object {
        private var mInstance: UserDataSourceImpl? = null
        fun getInstance(remote: UserDataSourceRemote): UserDataSourceImpl {
            if (mInstance == null) {
                mInstance = UserDataSourceImpl(remote)
            }
            return mInstance!!
        }
    }

    override fun login(username: String, password: String): Observable<UserData> {
        return mRemote.login(username, password)
    }

    override fun logout(userId: Int): Observable<UserData> {
        return mRemote.logout(userId)
    }

}