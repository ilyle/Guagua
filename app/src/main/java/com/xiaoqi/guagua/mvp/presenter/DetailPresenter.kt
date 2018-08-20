package com.xiaoqi.guagua.mvp.presenter

import com.xiaoqi.guagua.mvp.model.bean.EssayData.Data.Essay

interface DetailPresenter : BasePresenter {
    /**
     * 添加收藏
     */
    fun insertCollection(userId: Int, essay: Essay)

    /**
     * 取消添加收藏
     */
    fun removeCollection(userId: Int, essay: Essay)

    /**
     * 查询是否已经收藏
     */
    fun checkIsCollection(userId: Int, essay: Essay)
}