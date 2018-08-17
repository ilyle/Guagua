package com.xiaoqi.guagua.mvp.model.source

import com.xiaoqi.guagua.mvp.model.bean.EssayData
import io.reactivex.Observable

interface CollectionDataSource {

    fun getCollection(userId: Int): Observable<MutableList<EssayData.Data.Essay>>

    fun insertCollection(userId: Int, essayId: Int, timeStamp: Long)

    fun removeCollection(userId: Int, essayId: Int)

    fun isExist(userId: Int, essayId: Int): Boolean

    fun clearAll()
}