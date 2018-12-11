package com.xiaoqi.guagua

import android.Manifest
import android.app.Application
import android.content.Context
import android.content.pm.PackageManager
import android.support.v4.content.ContextCompat
import com.facebook.stetho.Stetho
import com.tencent.bugly.crashreport.CrashReport
import com.tencent.mmkv.MMKV
import com.xiaoqi.guagua.constant.Constant
import com.xiaoqi.guagua.util.ToastUtil
import com.ysbing.ypermission.PermissionManager
import org.litepal.LitePal


class MainApplication : Application() {
    companion object {
        private lateinit var instance: MainApplication

        @JvmStatic
        fun getContext(): Context {
            return instance.applicationContext
        }

        fun doAfterPermissionGain() {

        }
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        LitePal.initialize(this) // GuoLin的数据库初始化
        Stetho.initializeWithDefaults(this) // 可以查看本地数据库
        MMKV.initialize(this@MainApplication) // Tencent的MMKV初始化
        CrashReport.initCrashReport(this, Constant.BUGLY_APP_ID, true) // Tencent的Bugly初始化

        permissionCheck()

        val gainPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED
        if (gainPermission) {
            doAfterPermissionGain()
        }


    }

    private fun permissionCheck() {
        val t1 = System.currentTimeMillis()
        val permissions = arrayOf(
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_PHONE_STATE
        )
        PermissionManager.requestPermission(this, permissions, object : PermissionManager.PermissionsListener() {
            override fun onPermissionGranted() {
                // ToastUtil.showMsg("获取了所有权限，耗时：" + (System.currentTimeMillis() - t1))
            }

            override fun onPermissionDenied(noPermissionsList: List<PermissionManager.NoPermission>) {
                super.onPermissionDenied(noPermissionsList)
                val stringBuilder = StringBuilder()
                for (noPermission in noPermissionsList) {
                    stringBuilder.append(noPermission.permission).append("\n")
                }
                ToastUtil.showMsg("被拒绝的权限：\n" + stringBuilder.toString())
            }
        })
    }
}