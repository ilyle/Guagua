package com.xiaoqi.guagua.util

import com.tencent.mmkv.MMKV
import com.xiaoqi.guagua.constant.Constant

/**
 * Created by xujie on 2018/9/28.
 * MMKV工具类
 */
object MmkvUtil {

    fun setSearchHistory(str: String) {
        val searchHistorySet = getSearchHistory()
        searchHistorySet.add(str)
        MMKV.defaultMMKV().encode(Constant.SEARCH_HISTORY, searchHistorySet)
    }

    fun getSearchHistory(): MutableSet<String> {
        return MMKV.defaultMMKV().decodeStringSet(Constant.SEARCH_HISTORY, mutableSetOf())
    }
}