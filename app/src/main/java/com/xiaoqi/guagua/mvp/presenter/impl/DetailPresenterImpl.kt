package com.xiaoqi.guagua.mvp.presenter.impl

import com.xiaoqi.guagua.mvp.model.bean.Collection
import com.xiaoqi.guagua.mvp.model.source.CollectionDataSource
import com.xiaoqi.guagua.mvp.presenter.DetailPresenter
import com.xiaoqi.guagua.mvp.view.detail.DetailView

class DetailPresenterImpl(view: DetailView, model: CollectionDataSource) : DetailPresenter {

    private var mView = view
    private var mModel = model

    init {
        mView.setPresenter(this)
    }

    companion object {
        /**
         * 对外构造函数
         */
        fun build(view: DetailView, model: CollectionDataSource) {
            DetailPresenterImpl(view, model)
        }
    }

    override fun insertCollection(userId: Int, collection: Collection) {
        if (mModel.insertCollection(collection)){
            mView.addToCollectionSuccess()
        } else {
            mView.addToCollectionFail()
        }
    }

    override fun removeCollection(userId: Int, collection: Collection) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun checkIsCollection(userId: Int, collection: Collection) {
        mModel.isExist(userId, collection)
    }

    override fun subscribe() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun unSubscribe() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}