package com.xiaoqi.guagua.util

import android.content.Context
import android.net.ConnectivityManager

object NetWorkUtil {
    /**
     * 网络是否可用
     */
    fun isNetWorkAvailable(context: Context): Boolean {
        val manager = context.applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
                ?: return false
        val networkInfo = manager.activeNetworkInfo
        return networkInfo.isConnected
    }
}