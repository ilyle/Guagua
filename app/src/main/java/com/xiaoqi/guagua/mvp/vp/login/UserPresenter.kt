package com.xiaoqi.guagua.mvp.vp.login

interface UserPresenter {

    fun login(username: String, password: String)

    fun logout(uid: String)

    fun register(username: String, password: String)
}