package com.xiaoqi.guagua.mvp.model.source.impl

import com.xiaoqi.guagua.mvp.model.bean.Collection
import com.xiaoqi.guagua.mvp.model.source.CollectionDataSource
import com.xiaoqi.guagua.mvp.model.source.local.CollectionDataSourceLocal
import io.reactivex.Observable

class CollectionDataSourceImpl(collectionDataSourceLocal: CollectionDataSourceLocal) : CollectionDataSource {
    override fun getCollection(userId: Int): Observable<MutableList<Collection>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun insertCollection(collection: Collection): Boolean {
        return mCollectionDataSourceLocal.insertCollection(collection)
    }

    override fun removeCollection(userId: Int, collection: Collection): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun isExist(userId: Int, collection: Collection): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
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