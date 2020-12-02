package com.xiaoqi.guagua.mvp.model.source

import com.xiaoqi.guagua.mvp.model.bean.Article
import io.reactivex.Observable

interface CollectionDataSource {

    fun getCollection(userId: Int): Observable<MutableList<Article>>

    fun insertCollection(userId: Int, article: Article): Boolean

    fun removeCollection(userId: Int, article: Article): Boolean

    fun isExist(userId: Int, article: Article): Boolean

    fun clearAll()
}