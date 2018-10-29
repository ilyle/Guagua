package com.xiaoqi.guagua.mvp.vp.login

interface UserPresenter {

    fun login(username: String, password: String)

    fun logout(userId: Int)
}