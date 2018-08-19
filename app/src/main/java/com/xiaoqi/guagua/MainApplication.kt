package com.xiaoqi.guagua

import android.app.Application
import android.content.Context
import com.facebook.stetho.Stetho
import org.litepal.LitePal

class MainApplication : Application() {
    companion object {
        private lateinit var instance: MainApplication

        @JvmStatic fun getContext() : Context {
            return instance.applicationContext
        }
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        LitePal.initialize(this)
        Stetho.initializeWithDefaults(this);
    }
}