package com.xiaoqi.guagua.util

import android.content.Context
import com.xiaoqi.guagua.MainApplication
import com.xiaoqi.guagua.constant.Constant
import com.xiaoqi.guagua.mvp.model.bean.User

/**
 * Created by Xujie on 2018/11/1
 * mail: jiexu215936@sohu-inc.com
 */
object PreferenceUtil {

    private const val FILE_USER = "FILE_USER"

    @JvmStatic
    fun setUser(user: User) {
        val sp = MainApplication.getContext().getSharedPreferences(FILE_USER, Context.MODE_PRIVATE)
        sp.edit().putInt(Constant.SP.USER_ID, user.id)
                .putString(Constant.SP.USER_UID, user.uid)
                .putString(Constant.SP.USER_USERNAME, user.username)
                .putString(Constant.SP.USER_PASSWORD, user.password)
                .putString(Constant.SP.USER_TOKEN, user.token)
                .putBoolean(Constant.SP.USER_IS_LOGIN, true)
                .apply()
    }

    @JvmStatic
    fun getUser(): User? {
        val user = User()
        val sp = MainApplication.getContext().getSharedPreferences(FILE_USER, Context.MODE_PRIVATE)
        user.id = sp.getInt(Constant.SP.USER_ID, 0); if (user.id == 0) return null // id为0，返回null
        user.uid = sp.getString(Constant.SP.USER_UID, null) ?: return null // uid为null，返回null
        user.token = sp.getString(Constant.SP.USER_TOKEN, null) ?: return null // token为null，返回null
        user.username = sp.getString(Constant.SP.USER_USERNAME, null)
        user.password = sp.getString(Constant.SP.USER_PASSWORD, null)
        return user
    }

    @JvmStatic
    fun cleanUser() {
        val sp = MainApplication.getContext().getSharedPreferences(FILE_USER, Context.MODE_PRIVATE)
        sp.edit().clear().apply()
    }
}