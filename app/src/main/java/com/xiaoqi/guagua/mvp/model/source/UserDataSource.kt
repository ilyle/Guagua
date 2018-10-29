package com.xiaoqi.guagua.mvp.model.source

import io.reactivex.Observable
import com.xiaoqi.guagua.mvp.model.bean.UserData

interface UserDataSource {

    fun login(username: String, password: String): Observable<UserData>

    fun logout(userId: Int): Observable<UserData>
}