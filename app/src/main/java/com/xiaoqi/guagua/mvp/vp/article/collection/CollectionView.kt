package com.xiaoqi.guagua.mvp.vp.article.collection

import com.xiaoqi.guagua.mvp.model.bean.Article
import com.xiaoqi.guagua.mvp.vp.BaseView
import com.xiaoqi.guagua.mvp.vp.article.collection.CollectionPresenter
import io.reactivex.internal.operators.maybe.MaybeIsEmpty

interface CollectionView : BaseView<CollectionPresenter> {
    /**
     * 视图是否激活
     */
    fun isActive(): Boolean

    fun showCollection(articleList: MutableList<Article>)

    fun showEmpty(isEmpty: Boolean)
}