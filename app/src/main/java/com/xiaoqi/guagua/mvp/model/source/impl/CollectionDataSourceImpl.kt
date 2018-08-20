package com.xiaoqi.guagua.mvp.model.source.impl

import com.xiaoqi.guagua.mvp.model.bean.EssayData.Data.Essay
import com.xiaoqi.guagua.mvp.model.source.CollectionDataSource
import com.xiaoqi.guagua.mvp.model.source.local.CollectionDataSourceLocal
import io.reactivex.Observable

class CollectionDataSourceImpl(collectionDataSourceLocal: CollectionDataSourceLocal) : CollectionDataSource {
    override fun getCollection(userId: Int): Observable<MutableList<Essay>> {
        return mCollectionDataSourceLocal.getCollection(userId)
    }

    override fun insertCollection(userId: Int, essay: Essay): Boolean {
        return mCollectionDataSourceLocal.insertCollection(userId, essay)
    }

    override fun removeCollection(userId: Int, essay: Essay): Boolean {
        return mCollectionDataSourceLocal.removeCollection(userId, essay)
    }

    override fun isExist(userId: Int, essay: Essay): Boolean {
        return mCollectionDataSourceLocal.isExist(userId, essay)
    }

    override fun clearAll() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    companion object {

        private var instance: CollectionDataSourceImpl? = null

        fun getInstance(collectionDataSourceLocal: CollectionDataSourceLocal): CollectionDataSourceImpl {
            if (instance == null) {
                instance = CollectionDataSourceImpl(collectionDataSourceLocal)
            }
            return instance!!
        }
    }

    private val mCollectionDataSourceLocal = collectionDataSourceLocal

}