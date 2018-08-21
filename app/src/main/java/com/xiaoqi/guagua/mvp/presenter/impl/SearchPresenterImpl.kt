package com.xiaoqi.guagua.mvp.presenter.impl

import com.xiaoqi.guagua.mvp.model.source.ArticleDataSource
import com.xiaoqi.guagua.mvp.presenter.SearchPresenter
import com.xiaoqi.guagua.mvp.view.search.SearchView

class SearchPresenterImpl(view: SearchView, model: ArticleDataSource) : SearchPresenter {

    private val mView = view
    private val mModel = model

    init {
        mView.setPresenter(this)
    }

    override fun subscribe() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun unSubscribe() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun queryArticle(page: Int, query: String, forceUpdate: Boolean, cleanCache: Boolean) {

    }

}