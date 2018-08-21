package com.xiaoqi.guagua.mvp.vp.search

import com.xiaoqi.guagua.mvp.vp.BasePresenter

interface SearchPresenter : BasePresenter {
    fun queryArticle(page: Int, query: String, forceUpdate: Boolean, cleanCache: Boolean)
}