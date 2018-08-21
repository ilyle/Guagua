package com.xiaoqi.guagua.mvp.vp.detail

import com.xiaoqi.guagua.mvp.model.bean.Article
import com.xiaoqi.guagua.mvp.model.source.CollectionDataSource

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

    override fun insertCollection(userId: Int, article: Article) {
        if (mModel.insertCollection(userId, article)){
            mView.addToCollectionSuccess()
        } else {
            mView.addToCollectionFail()
        }
    }

    override fun removeCollection(userId: Int, article: Article) {
        if (mModel.removeCollection(userId, article)) {
            mView.removeCollectionSuccess()
        } else {
            mView.removeCollectionFail()
        }
    }

    override fun checkIsCollection(userId: Int, article: Article) {
        mView.saveCollectionStatus(mModel.isExist(userId, article))
    }

    override fun subscribe() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun unSubscribe() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}