package com.xiaoqi.guagua.mvp.view

import android.view.View

interface BaseView<T> {
    fun initView(view: View)

    fun setPresenter(presenter: T)
}