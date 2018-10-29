package com.xiaoqi.guagua.mvp.vp.login

import com.xiaoqi.guagua.mvp.model.bean.User
import com.xiaoqi.guagua.mvp.vp.BaseView

interface LoginView : BaseView<UserPresenter> {

    fun isActive(): Boolean

    fun loginSuccess(user: User)

    fun showLoginFail(errorMsg: String)

    fun showNetworkError(errorMsg: String)
}