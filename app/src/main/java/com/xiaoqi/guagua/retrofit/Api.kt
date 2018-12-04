package com.xiaoqi.guagua.retrofit

object Api {

    const val API_WAN_ANDROID = "http://www.wanandroid.com/"

    const val ARTICLE_LIST = API_WAN_ANDROID + "article/list/"

    const val ARTICLE_QUERY = API_WAN_ANDROID + "article/query/"

    const val CATEGORY_LIST = API_WAN_ANDROID + "tree/json"

    const val BANNER = API_WAN_ANDROID + "banner/json"

    const val API_GUA_GUA = "http://106.12.130.178:8080/GuaguaWeb/"

    const val LOGIN = API_GUA_GUA + "login"

    const val LOGIN_TOKEN = "$LOGIN/token"

    const val LOGOUT = API_GUA_GUA + "logout"

    const val REGISTER = API_GUA_GUA + "register"
}