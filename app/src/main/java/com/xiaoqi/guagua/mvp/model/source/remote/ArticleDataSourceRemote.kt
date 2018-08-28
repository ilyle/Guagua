package com.xiaoqi.guagua.mvp.model.source.remote

import com.xiaoqi.guagua.mvp.model.bean.Article
import com.xiaoqi.guagua.mvp.model.source.ArticleDataSource
import com.xiaoqi.guagua.retrofit.RetrofitClient
import com.xiaoqi.guagua.retrofit.RetrofitService
import com.xiaoqi.guagua.util.SortDescendUtil
import io.reactivex.Observable

/**
 * 文章数据类，远程获取
 */
class ArticleDataSourceRemote private constructor() : ArticleDataSource {

    companion object {
        private var mInstance: ArticleDataSourceRemote? = null

        fun getInstance(): ArticleDataSourceRemote {
            if (mInstance == null) {
                mInstance = ArticleDataSourceRemote()
            }
            return mInstance!!
        }
    }

    override fun listArticle(page: Int, forceUpdate: Boolean, clearCache: Boolean): Observable<MutableList<Article>> {
        return RetrofitClient.getInstance()
                .create(RetrofitService::class.java)
                .listArticle(page)
                .filter { it.errorCode != -1 }
                .flatMap { Observable.fromIterable(it.data?.datas).toSortedList { a1, a2 -> SortDescendUtil.sortArticle(a1, a2) }.toObservable() }
    }

    override fun queryArticle(page: Int, query: String, forceUpdate: Boolean, clearCache: Boolean): Observable<MutableList<Article>> {
        return RetrofitClient.getInstance()
                .create(RetrofitService::class.java)
                .queryArticle(page, query)
                .filter { it.errorCode != -1 }
                .flatMap { Observable.fromIterable(it.data?.datas).toSortedList { a1, a2 -> SortDescendUtil.sortArticle(a1, a2) }.toObservable() }
    }
}