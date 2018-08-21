package com.xiaoqi.guagua.mvp.vp.article.collection

import com.xiaoqi.guagua.mvp.vp.BasePresenter

interface CollectionPresenter : BasePresenter {
    fun getCollection(userId: Int)
}