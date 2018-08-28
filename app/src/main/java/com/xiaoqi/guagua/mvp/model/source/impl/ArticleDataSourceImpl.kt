package com.xiaoqi.guagua.mvp.model.source.impl

import com.xiaoqi.guagua.mvp.model.bean.Article
import com.xiaoqi.guagua.mvp.model.source.ArticleDataSource
import com.xiaoqi.guagua.mvp.model.source.remote.ArticleDataSourceRemote
import com.xiaoqi.guagua.util.SortDescendUtil
import io.reactivex.Observable

class ArticleDataSourceImpl(remote: ArticleDataSourceRemote) : ArticleDataSource {

    private var mRemote = remote

    companion object {

        private var mInstance: ArticleDataSourceImpl? = null

        fun getInstance(remote: ArticleDataSourceRemote): ArticleDataSourceImpl {
            if (mInstance == null) {
                mInstance = ArticleDataSourceImpl(remote)
            }
            return mInstance!!
        }
    }

    private var mArticleCache: LinkedHashMap<Int, Article>? = null // 缓存数据

    override fun listArticle(page: Int, forceUpdate: Boolean, clearCache: Boolean): Observable<MutableList<Article>> {
        /*
        !forceUpdate，不更新，即用户按HOME键再返回APP，此时返回缓存的文章列表
         */
        if (!forceUpdate && mArticleCache != null) {
            return Observable.fromIterable(ArrayList(mArticleCache!!.values))
                    .toSortedList { a1, a2 -> SortDescendUtil.sortArticle(a1, a2) }
                    .toObservable()
        }
        /*
        forceUpdate&&!clearCache，更新且不清缓存，即用户向下滑动列表的情况，此时请求数据并保存缓存
         */
        if (!clearCache && mArticleCache != null) {
            val ob1 = Observable.fromIterable(ArrayList(mArticleCache!!.values))
                    .toSortedList { a1, a2 -> SortDescendUtil.sortArticle(a1, a2) }
                    .toObservable()
            /*
            请求新的数据
             */
            val ob2 = mRemote.listArticle(page, forceUpdate, clearCache)
                    .doOnNext { refreshArticleCache(clearCache, it) }
            /*
            合并数据
             */
            return Observable.merge(ob1, ob2).collect({ mutableListOf<Article>() }, { t1, t2 -> t1.addAll(t2) }).toObservable()
        }
        /*
        forceUpdate && cleanCache，更新且清缓存，即下拉刷新，还有第一次加载的情况
         */
        return mRemote.listArticle(0, forceUpdate, clearCache).doOnNext { refreshArticleCache(clearCache, it) }
    }

    override fun queryArticle(page: Int, query: String, forceUpdate: Boolean, clearCache: Boolean): Observable<MutableList<Article>> {
        return mRemote.queryArticle(0, query, forceUpdate, clearCache)
    }

    /**
     * 刷新文章缓存
     */
    private fun refreshArticleCache(clearCache: Boolean, articleList: List<Article>) {
        if (mArticleCache == null) {
            mArticleCache = LinkedHashMap()
        }
        if (clearCache) {
            mArticleCache?.clear()
        }
        for (article in articleList) {
            mArticleCache?.put(article.articleId, article)
        }
    }

}