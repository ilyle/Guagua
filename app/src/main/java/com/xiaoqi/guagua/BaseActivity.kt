package com.xiaoqi.guagua

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.noober.background.BackgroundLibrary

/**
* Created by xujie on 2018/12/4.
* Mail : 617314917@qq.com
*/
abstract class BaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        BackgroundLibrary.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(getResource())
    }

    /**
     * 获取布局文件R.layout.activity_abc
     */
    abstract fun getResource(): Int
}