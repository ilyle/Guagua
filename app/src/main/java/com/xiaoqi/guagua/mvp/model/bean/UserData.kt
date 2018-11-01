package com.xiaoqi.guagua.mvp.model.bean

import com.xiaoqi.guagua.util.MmkvUtil
import com.xiaoqi.guagua.util.PreferenceUtil

data class UserData(
        var data: User?,
        var errorCode: Int,
        var errorMsg: String?
)

class User {
    var id: Int = 0 // 0：未登录
    var uid: String? = null // 服务器生成的
    var username: String? = null
    var password: String? = null
    var token: String? = null
}

object UserInfo {

    var user: User? = null
        get() {
            return PreferenceUtil.getUser()
        }
}