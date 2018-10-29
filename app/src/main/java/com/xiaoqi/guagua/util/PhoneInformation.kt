package com.xiaoqi.guagua.util

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.provider.Settings
import android.telephony.TelephonyManager
import android.text.TextUtils
import com.xiaoqi.guagua.MainApplication

object PhoneInformation {

    var imei: String? = null
        get() {
            if (TextUtils.isEmpty(field)) {
                val context = MainApplication.getContext()
                val deviceId = (context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager).deviceId
                field = if (!TextUtils.isEmpty(deviceId)) {
                    deviceId
                } else {
                    Settings.System.getString(context.contentResolver, Settings.Secure.ANDROID_ID)
                }
            }
            return field
        }

    var deviceName: String? = null
        get() {
            if (TextUtils.isEmpty(field)) {
                val context = MainApplication.getContext()
                field = if (Build.VERSION.SDK_INT >= 25)
                    Settings.Global.getString(context.contentResolver, Settings.Global.DEVICE_NAME)
                else
                    Build.MODEL
            }
            return field
        }


}