package com.xiaoqi.guagua.mvp.presenter

import com.xiaoqi.guagua.mvp.model.bean.Article

interface DetailPresenter : BasePresenter {
    /**
     * 添加收藏
     */
    fun insertCollection(userId: Int, article: Article)

    /**
     * 取消添加收藏
     */
    fun removeCollection(userId: Int, article: Article)

    /**
     * 查询是否已经收藏
     */
    fun checkIsCollection(userId: Int, article: Article)
}