package com.xiaoqi.guagua.mvp.model.source

import com.xiaoqi.guagua.mvp.model.bean.Essay
import io.reactivex.Observable

interface CollectionDataSource {

    fun getCollection(userId: Int): Observable<MutableList<Essay>>

    fun insertCollection(userId: Int, essay: Essay): Boolean

    fun removeCollection(userId: Int, essay: Essay): Boolean

    fun isExist(userId: Int, essay: Essay): Boolean

    fun clearAll()
}