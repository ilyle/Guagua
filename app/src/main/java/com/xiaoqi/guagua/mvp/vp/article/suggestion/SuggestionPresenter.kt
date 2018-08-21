package com.xiaoqi.guagua.mvp.vp.article.suggestion

import com.xiaoqi.guagua.mvp.vp.BasePresenter

interface SuggestionPresenter : BasePresenter {
    fun listArticle(page: Int, forceUpdate: Boolean, cleanCache: Boolean)
}