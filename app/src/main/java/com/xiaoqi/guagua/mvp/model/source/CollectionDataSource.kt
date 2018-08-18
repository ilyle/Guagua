package com.xiaoqi.guagua.mvp.model.source

import com.xiaoqi.guagua.mvp.model.bean.Collection
import io.reactivex.Observable

interface CollectionDataSource {

    fun getCollection(userId: Int): Observable<MutableList<Collection>>

    fun insertCollection(collection: Collection): Boolean

    fun removeCollection(userId: Int, collection: Collection): Boolean

    fun isExist(userId: Int, collection: Collection): Boolean

    fun clearAll()
}