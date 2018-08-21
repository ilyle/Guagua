package com.xiaoqi.guagua.mvp.presenter.impl

import com.xiaoqi.guagua.mvp.model.source.EssayDataSource
import com.xiaoqi.guagua.mvp.presenter.SearchPresenter
import com.xiaoqi.guagua.mvp.view.BaseView
import com.xiaoqi.guagua.mvp.view.SearchFragment

class SearchPresenterImpl(view: SearchFragment, model: EssayDataSource) : SearchPresenter {

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

    override fun searchEssay(page: Int, query: String, forceUpdate: Boolean, cleanCache: Boolean) {

    }

}