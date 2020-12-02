package com.xiaoqi.guagua.mvp.vp.detail

import com.xiaoqi.guagua.mvp.vp.BaseView
import com.xiaoqi.guagua.mvp.vp.detail.DetailPresenter

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
     * 取消收藏成功
     */
    fun removeCollectionSuccess()

    /**
     * 取消收藏失败
     */

    fun removeCollectionFail()
    /**
     * 保存收藏状态
     */
    fun saveCollectionStatus(isCollected: Boolean)
}