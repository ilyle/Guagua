package com.xiaoqi.guagua.mvp.vp.login

import com.xiaoqi.guagua.mvp.vp.BasePresenter

interface UserPresenter : BasePresenter{

    fun login(username: String, password: String)

    fun login(token: String)

    fun logout(uid: String)

    fun register(username: String, password: String)
}