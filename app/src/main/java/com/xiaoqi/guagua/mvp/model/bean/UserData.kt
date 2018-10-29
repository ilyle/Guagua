package com.xiaoqi.guagua.mvp.model.bean

import com.xiaoqi.guagua.util.MmkvUtil

data class UserData(
        var data: User?,
        var errorCode: Int,
        var errorMsg: String?
)

data class User(
        var id: Int, // -1：未登录
        var username: String,
        var password: String,
        var token: String
)

object UserInfo {
    var user: User = User(-1, "", "", "")
        get() {
            val id = MmkvUtil.getUserId()
            val username = MmkvUtil.getUserName()
            val password = MmkvUtil.getUserPw()
            field = User(id, username, password, "")
            return field
        }
}