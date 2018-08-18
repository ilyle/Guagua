package com.xiaoqi.guagua.mvp.presenter

import com.xiaoqi.guagua.mvp.model.bean.Collection

interface DetailPresenter : BasePresenter {
    fun insertCollection(userId: Int, collection: Collection)

    fun removeCollection(userId: Int, collection: Collection)

    fun checkIsCollection(userId: Int, collection: Collection)
}