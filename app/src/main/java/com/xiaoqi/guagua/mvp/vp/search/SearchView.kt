package com.xiaoqi.guagua.mvp.vp.search

import com.xiaoqi.guagua.mvp.model.bean.Article
import com.xiaoqi.guagua.mvp.vp.BaseView
import com.xiaoqi.guagua.mvp.vp.search.SearchPresenter

interface SearchView : BaseView<SearchPresenter> {
    /**
     * 视图是否激活
     */
    fun isActive(): Boolean

    fun showArticle(articleList: List<Article>)

    fun showEmpty(toShow: Boolean)
}