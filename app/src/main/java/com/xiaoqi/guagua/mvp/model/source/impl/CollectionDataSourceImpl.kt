package com.xiaoqi.guagua.mvp.model.source.impl

import com.xiaoqi.guagua.mvp.model.bean.Article
import com.xiaoqi.guagua.mvp.model.source.CollectionDataSource
import com.xiaoqi.guagua.mvp.model.source.local.CollectionDataSourceLocal
import io.reactivex.Observable

class CollectionDataSourceImpl(collectionDataSourceLocal: CollectionDataSourceLocal) : CollectionDataSource {
    override fun getCollection(userId: Int): Observable<MutableList<Article>> {
        return mCollectionDataSourceLocal.getCollection(userId)
    }

    override fun insertCollection(userId: Int, article: Article): Boolean {
        return mCollectionDataSourceLocal.insertCollection(userId, article)
    }

    override fun removeCollection(userId: Int, article: Article): Boolean {
        return mCollectionDataSourceLocal.removeCollection(userId, article)
    }

    override fun isExist(userId: Int, article: Article): Boolean {
        return mCollectionDataSourceLocal.isExist(userId, article)
    }

    override fun clearAll() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    companion object {

        private var mInstance: CollectionDataSourceImpl? = null

        fun getInstance(collectionDataSourceLocal: CollectionDataSourceLocal): CollectionDataSourceImpl {
            if (mInstance == null) {
                mInstance = CollectionDataSourceImpl(collectionDataSourceLocal)
            }
            return mInstance!!
        }
    }

    private val mCollectionDataSourceLocal = collectionDataSourceLocal

}