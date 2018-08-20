package com.xiaoqi.guagua.mvp.presenter.impl

import com.xiaoqi.guagua.mvp.model.bean.EssayData.Data.Essay
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

    override fun insertCollection(userId: Int, essay: Essay) {
        if (mModel.insertCollection(userId, essay)){
            mView.addToCollectionSuccess()
        } else {
            mView.addToCollectionFail()
        }
    }

    override fun removeCollection(userId: Int, essay: Essay) {
        if (mModel.removeCollection(userId, essay)) {
            mView.removeCollectionSuccess()
        } else {
            mView.removeCollectionFail()
        }
    }

    override fun checkIsCollection(userId: Int, essay: Essay) {
        mView.saveCollectionStatus(mModel.isExist(userId, essay))
    }

    override fun subscribe() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun unSubscribe() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}