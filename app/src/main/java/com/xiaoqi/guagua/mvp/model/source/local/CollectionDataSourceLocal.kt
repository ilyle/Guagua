package com.xiaoqi.guagua.mvp.model.source.local

import com.xiaoqi.guagua.mvp.model.bean.Collection
import com.xiaoqi.guagua.mvp.model.source.CollectionDataSource
import io.reactivex.Observable
import org.litepal.LitePal

class CollectionDataSourceLocal : CollectionDataSource {
    override fun getCollection(userId: Int): Observable<MutableList<Collection>> {
        val collectionList = LitePal.where("userId = ", userId.toString()).find(Collection::class.java)
        return Observable.fromIterable(collectionList).toSortedList {
            c1, c2 -> if (c1.timestamp > c2.timestamp) -1 else 1
        }.toObservable()
    }

    override fun insertCollection(collection: Collection): Boolean {
        return collection.save()
    }

    override fun removeCollection(userId: Int, collection: Collection): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun isExist(userId: Int, collection: Collection): Boolean {
        val essayList = LitePal
                .where("userId = ? and essayId = ?", userId.toString(), collection.essayId.toString())
                .find(Collection::class.java)
        return essayList.isEmpty()
    }

    override fun clearAll() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    companion object {
        private var singleInstance: CollectionDataSourceLocal? = null

        fun getInstance(): CollectionDataSourceLocal {
            if (singleInstance == null) {
                singleInstance = CollectionDataSourceLocal()
            }
            return singleInstance!!
        }
    }


}