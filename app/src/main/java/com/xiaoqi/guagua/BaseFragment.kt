package com.xiaoqi.guagua

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

abstract class BaseFragment : Fragment(){

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(getResource(), container, false)
        initView(view)
        return view
    }

    /**
     * 获取布局文件R.layout.fragment_abc
     */
    abstract fun getResource(): Int

    /**
     * 初始化布局文件内的控件
     */
    abstract fun initView(view: View)
}