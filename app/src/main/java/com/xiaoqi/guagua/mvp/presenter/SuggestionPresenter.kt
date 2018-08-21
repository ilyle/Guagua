package com.xiaoqi.guagua.mvp.presenter

interface SuggestionPresenter : BasePresenter {
    fun listArticle(page: Int, forceUpdate: Boolean, cleanCache: Boolean)
}