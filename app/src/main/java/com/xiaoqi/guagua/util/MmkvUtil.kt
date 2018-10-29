package com.xiaoqi.guagua.util

import com.tencent.mmkv.MMKV
import com.xiaoqi.guagua.constant.Constant
import java.lang.StringBuilder

/**
 * Created by xujie on 2018/9/28.
 * MMKV工具类
 */
object MmkvUtil {

    private val mmkv = MMKV.defaultMMKV()

    fun setSearchHistory(str: String) {
        val historyList = getSearchHistory()
        val builder = StringBuilder(str)
        historyList.filter { it != str }.forEach { builder.append(",$it") }
        mmkv.encode(Constant.MMKV.SEARCH_HISTORY, builder.toString())
    }

    fun getSearchHistory(): MutableList<String> {
        val tempStr = mmkv.decodeString(Constant.MMKV.SEARCH_HISTORY, "")
        var ret = mutableListOf<String>()
        if (!tempStr.isEmpty()) {
            ret = tempStr.split(",").toMutableList()
        }
        return ret
    }

    /**
     * 清除单个搜索记录
     */
    fun cleanSearchHistory(str: String) {
        val historyList: MutableList<String> = getSearchHistory()
        historyList.remove(str)
        val builder = StringBuilder()
        if (!historyList.isEmpty()) {
            historyList.forEach { builder.append("$it,") }
            builder.deleteCharAt(builder.length - 1)
        }
        mmkv.encode(Constant.MMKV.SEARCH_HISTORY, builder.toString())
    }

    /**
     * 清除全部搜索记录
     */
    fun cleanSearchHistory() {
        mmkv.encode(Constant.MMKV.SEARCH_HISTORY, "")
    }

    fun setUserId(userId: Int) {
        mmkv.encode(Constant.MMKV.USER_ID, userId)
    }

    fun getUserId(): Int {
        return mmkv.decodeInt(Constant.MMKV.USER_ID, -1)
    }

    fun setUserName(username: String) {
        mmkv.encode(Constant.MMKV.USER_NAME, username)
    }

    fun getUserName(): String {
        return mmkv.decodeString(Constant.MMKV.USER_NAME, "")
    }

    fun setUserPw(password: String) {
        mmkv.encode(Constant.MMKV.USER_PW, password)
    }

    fun getUserPw(): String {
        return mmkv.decodeString(Constant.MMKV.USER_PW, "")
    }

    fun setUserIsLogin(userIsLogin: Boolean) {
        mmkv.encode(Constant.MMKV.USER_IS_LOGIN, userIsLogin)
    }

    fun getUserIsLogin(): Boolean {
        return mmkv.decodeBool(Constant.MMKV.USER_IS_LOGIN, false)
    }
}