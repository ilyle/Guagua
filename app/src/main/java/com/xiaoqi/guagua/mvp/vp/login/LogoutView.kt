package com.xiaoqi.guagua.mvp.vp.login

import com.xiaoqi.guagua.mvp.model.bean.User
import com.xiaoqi.guagua.mvp.model.bean.UserData
import com.xiaoqi.guagua.mvp.vp.BaseView

interface LogoutView : BaseView<UserPresenter> {

    fun isActive(): Boolean

    fun logoutSuccess(user: User)

    fun showLogoutFail(errorMsg: String)

    fun showNetworkError(errorMsg: String)
}