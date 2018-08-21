package com.xiaoqi.guagua.mvp.view.article.collection

import com.xiaoqi.guagua.mvp.model.bean.Article
import com.xiaoqi.guagua.mvp.presenter.CollectionPresenter
import com.xiaoqi.guagua.mvp.view.BaseView

interface CollectionView : BaseView<CollectionPresenter> {
    /**
     * 视图是否激活
     */
    fun isActive(): Boolean

    fun showCollection(articleList: MutableList<Article>)

    fun showEmptyView(toShow: Boolean)
}