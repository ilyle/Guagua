package com.xiaoqi.guagua.util

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import com.xiaoqi.guagua.MainApplication

object AppUtil {

    fun getVersionName(): String {
        val context = MainApplication.getContext()
        val manager = context.packageManager
        return manager.getPackageInfo(context.packageName, 0).versionName
    }

    fun getVersionCode(): Int {
        val context = MainApplication.getContext()
        val manager = context.packageManager
        return manager.getPackageInfo(context.packageName, 0).versionCode
    }

    fun openInBrowser(context: Context?, url: String?) {
        url?.let {
            try {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                context?.startActivity(intent)
            } catch (e: ActivityNotFoundException) {
                ToastUtil.showMsg(e.toString())
            }
        }
    }
}