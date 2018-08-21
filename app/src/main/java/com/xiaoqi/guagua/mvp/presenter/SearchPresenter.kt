package com.xiaoqi.guagua.mvp.presenter

interface SearchPresenter : BasePresenter {
    fun queryArticle(page: Int, query: String, forceUpdate: Boolean, cleanCache: Boolean)
}