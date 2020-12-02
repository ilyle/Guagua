package com.xiaoqi.guagua.mvp.vp.login

import com.xiaoqi.guagua.mvp.model.bean.User
import com.xiaoqi.guagua.mvp.model.bean.UserData
import com.xiaoqi.guagua.mvp.vp.BaseView

interface MineView : BaseView<UserPresenter> {

    fun isActive(): Boolean

    fun logoutSuccess(user: User)

    fun showLogoutFail(errorMsg: String)

    fun showNetworkError(errorMsg: String)

    /**
     * 更换头像成功
     */
    fun showUpdateAvatarSuccess(user: User)

    /**
     * 更换头像失败
     */
    fun showUpdateAvatarFail(errorMsg: String)
}