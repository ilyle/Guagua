package com.xiaoqi.guagua.mvp.view.article.suggestion

import com.xiaoqi.guagua.mvp.model.bean.Article
import com.xiaoqi.guagua.mvp.presenter.SuggestionPresenter
import com.xiaoqi.guagua.mvp.view.BaseView

interface SuggestionView : BaseView<SuggestionPresenter> {

    /**
     * 视图是否激活
     */
    fun isActive(): Boolean

    fun setLoadingIndicator(isActive: Boolean)

    fun showArticle(articleList: List<Article>)

    fun showEmpty(toShow: Boolean)
}