package com.xiaoqi.guagua.mvp.vp.login

import com.xiaoqi.guagua.mvp.model.bean.User
import com.xiaoqi.guagua.mvp.vp.BaseView

/**
 * Created by Xujie on 2018/10/31
 * mail: jiexu215936@sohu-inc.com
 */
interface RegisterView: BaseView<UserPresenter> {

    fun isActive(): Boolean

    fun registerSuccess(user: User)

    fun showRegisterFail(errorMsg: String)

    fun showNetworkError(errorMsg: String)
}