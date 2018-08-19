package com.xiaoqi.guagua.mvp.presenter

import com.xiaoqi.guagua.mvp.model.bean.Collection

interface DetailPresenter : BasePresenter {
    /**
     * 添加收藏
     */
    fun insertCollection(userId: Int, collection: Collection)

    /**
     * 取消添加收藏
     */
    fun removeCollection(userId: Int, collection: Collection)

    /**
     * 查询是否已经收藏
     */
    fun checkIsCollection(userId: Int, collection: Collection)
}