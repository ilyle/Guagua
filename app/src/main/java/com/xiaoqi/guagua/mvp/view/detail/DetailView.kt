package com.xiaoqi.guagua.mvp.view.detail

import com.xiaoqi.guagua.mvp.presenter.DetailPresenter
import com.xiaoqi.guagua.mvp.view.BaseView

interface DetailView : BaseView<DetailPresenter> {

    /**
     * 添加收藏成功
     */
    fun addToCollectionSuccess()

    /**
     * 添加收藏失败
     */
    fun addToCollectionFail()

    /**
     * 保存收藏状态
     */
    fun saveCollectionStatus(isCollected: Boolean)
}