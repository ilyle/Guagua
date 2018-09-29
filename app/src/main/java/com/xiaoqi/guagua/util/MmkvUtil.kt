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
        mmkv.encode(Constant.SEARCH_HISTORY, builder.toString())
    }

    fun getSearchHistory(): List<String> {
        val tempStr = mmkv.decodeString(Constant.SEARCH_HISTORY, "")
        var ret = listOf<String>()
        if (!tempStr.isEmpty()) {
            ret = tempStr.split(",")
        }
        return ret
    }

    fun cleanSearchHistory() {
        mmkv.clear()
    }
}