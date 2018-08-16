package com.xiaoqi.guagua

import android.app.Application
import android.content.Context

class BaseApplication : Application() {
    companion object {
        private var instance: BaseApplication? = null

        fun getContext() : Context {
            return instance!!.applicationContext
        }
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }
}