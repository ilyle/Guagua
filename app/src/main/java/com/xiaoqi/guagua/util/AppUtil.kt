package com.xiaoqi.guagua.util

import com.xiaoqi.guagua.MainApplication

object AppUtil {

    fun getVersionCode(): String {
        val context = MainApplication.getContext()
        val manager = context.packageManager
        return manager.getPackageInfo(context.packageName, 0).versionName
    }
}