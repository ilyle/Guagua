package com.xiaoqi.guagua.mvp.vp.article.suggestion

import com.xiaoqi.guagua.mvp.model.bean.Article
import com.xiaoqi.guagua.mvp.vp.BaseView

interface SuggestionView : BaseView<SuggestionPresenter> {

    /**
     * 视图是否激活
     */
    fun isActive(): Boolean

    fun setLoadingIndicator(isRefreshing: Boolean)

    fun showArticle(articleList: List<Article>)

    fun showEmpty(isEmpty: Boolean)
}