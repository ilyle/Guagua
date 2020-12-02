package com.xiaoqi.guagua.util

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri

object AppUtil {

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