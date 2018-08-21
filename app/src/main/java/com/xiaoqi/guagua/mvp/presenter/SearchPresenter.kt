package com.xiaoqi.guagua.mvp.presenter

interface SearchPresenter : BasePresenter {
    fun searchEssay(page: Int, query: String, forceUpdate: Boolean, cleanCache: Boolean)
}