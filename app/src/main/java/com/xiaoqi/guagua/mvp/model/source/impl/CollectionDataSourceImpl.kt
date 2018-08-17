package com.xiaoqi.guagua.mvp.model.source.impl

import com.xiaoqi.guagua.mvp.model.bean.EssayData
import com.xiaoqi.guagua.mvp.model.source.CollectionDataSource
import com.xiaoqi.guagua.mvp.model.source.local.CollectionDataSourceLocal
import io.reactivex.Observable

class CollectionDataSourceImpl(collectionDataSourceLocal: CollectionDataSourceLocal) : CollectionDataSource {

    companion object {

        private var instance: CollectionDataSourceImpl? = null

        fun getInstance(collectionDataSourceLocal: CollectionDataSourceLocal): CollectionDataSourceImpl {
            if (instance == null) {
                instance = CollectionDataSourceImpl(collectionDataSourceLocal)
            }
            return instance!!
        }
    }

    override fun getCollection(userId: Int): Observable<MutableList<EssayData.Data.Essay>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun insertCollection(userId: Int, essayId: Int, timeStamp: Long) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun removeCollection(userId: Int, essayId: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun isExist(userId: Int, essayId: Int): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun clearAll() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private val mCollectionDataSourceLocal = collectionDataSourceLocal

}