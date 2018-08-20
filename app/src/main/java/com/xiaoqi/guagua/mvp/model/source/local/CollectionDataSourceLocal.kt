package com.xiaoqi.guagua.mvp.model.source.local

import com.xiaoqi.guagua.mvp.model.bean.Essay
import com.xiaoqi.guagua.mvp.model.source.CollectionDataSource
import io.reactivex.Observable
import org.litepal.LitePal

class CollectionDataSourceLocal : CollectionDataSource {
    override fun getCollection(userId: Int): Observable<MutableList<Essay>> {
        val essayList = LitePal.where("userId = ?", userId.toString()).find(Essay::class.java)
        return Observable.fromIterable(essayList).toSortedList {
            c1, c2 -> if (c1.timestamp > c2.timestamp) -1 else 1
        }.toObservable()
    }

    override fun insertCollection(userId: Int, essay: Essay): Boolean {
        return essay.save()
    }

    override fun removeCollection(userId: Int, essay: Essay): Boolean {
        val ret = LitePal.deleteAll(Essay::class.java, "userId = ? and essayId = ?", userId.toString(), essay.essayId.toString())
        return ret != -1
    }

    override fun isExist(userId: Int, essay: Essay): Boolean {
        val essayList = LitePal
                .where("userId = ? and essayId = ?", userId.toString(), essay.essayId.toString())
                .find(Essay::class.java)
        return !essayList.isEmpty()
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