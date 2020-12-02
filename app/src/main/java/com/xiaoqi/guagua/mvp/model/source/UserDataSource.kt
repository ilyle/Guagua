package com.xiaoqi.guagua.mvp.model.source

import io.reactivex.Observable
import com.xiaoqi.guagua.mvp.model.bean.UserData
import okhttp3.MultipartBody

interface UserDataSource {

    fun login(username: String, password: String): Observable<UserData>

    fun login(token: String): Observable<UserData>

    fun logout(uid: String): Observable<UserData>

    fun register(username: String, password: String): Observable<UserData>

    fun updateAvatar(uid: String, username: String, avatar: MultipartBody.Part): Observable<UserData>
}