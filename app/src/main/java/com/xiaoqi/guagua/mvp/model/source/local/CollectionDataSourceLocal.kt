package com.xiaoqi.guagua.mvp.model.source.local

import com.xiaoqi.guagua.mvp.model.bean.Article
import com.xiaoqi.guagua.mvp.model.source.CollectionDataSource
import io.reactivex.Observable
import org.litepal.LitePal

class CollectionDataSourceLocal : CollectionDataSource {
    override fun getCollection(userId: Int): Observable<MutableList<Article>> {
        val articleList = LitePal.where("userId = ?", userId.toString()).find(Article::class.java)
        return Observable.fromIterable(articleList).toSortedList {
            a1, a2 -> if (a1.timestamp > a2.timestamp) -1 else 1
        }.toObservable()
    }

    override fun insertCollection(userId: Int, article: Article): Boolean {
        return article.save()
    }

    override fun removeCollection(userId: Int, article: Article): Boolean {
        val ret = LitePal.deleteAll(Article::class.java, "userId = ? and articleId = ?", userId.toString(), article.articleId.toString())
        return ret != -1
    }

    override fun isExist(userId: Int, article: Article): Boolean {
        val articleList = LitePal
                .where("userId = ? and articleId = ?", userId.toString(), article.articleId.toString())
                .find(Article::class.java)
        return !articleList.isEmpty()
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