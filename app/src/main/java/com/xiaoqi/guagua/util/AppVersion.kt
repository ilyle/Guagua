package com.xiaoqi.guagua.util

import android.text.TextUtils
import com.xiaoqi.guagua.MainApplication

object AppVersion {

    var versionName: String? = null
        get() {
            if (TextUtils.isEmpty(field)) {
                val context = MainApplication.getContext()
                field = MainApplication.getContext().packageManager.getPackageInfo(context.packageName, 0).versionName
            }
            return field
        }

    var versionCode: Int = 0
        get() {
            if (field <= 0) {
                val context = MainApplication.getContext()
                field = context.packageManager.getPackageInfo(context.packageName, 0).versionCode
            }
            return field
        }
}