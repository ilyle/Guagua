package com.xiaoqi.guagua.mvp.view.detail

import com.xiaoqi.guagua.mvp.presenter.DetailPresenter
import com.xiaoqi.guagua.mvp.view.BaseView

interface DetailView : BaseView<DetailPresenter> {

    fun addToCollectionSuccess()

    fun addToCollectionFail()
}