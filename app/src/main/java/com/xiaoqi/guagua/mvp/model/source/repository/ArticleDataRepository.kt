package com.xiaoqi.guagua.mvp.model.source.repository

import com.xiaoqi.guagua.mvp.model.bean.Article
import com.xiaoqi.guagua.mvp.model.source.ArticleDataSource
import com.xiaoqi.guagua.mvp.model.source.remote.ArticleDataSourceRemote
import com.xiaoqi.guagua.util.SortDescendUtil
import io.reactivex.Observable

class ArticleDataRepository(remote: ArticleDataSourceRemote) : ArticleDataSource {

    private var mRemote = remote

    companion object {

        private var mInstance: ArticleDataRepository? = null

        fun getInstance(remote: ArticleDataSourceRemote): ArticleDataRepository {
            if (mInstance == null) {
                mInstance = ArticleDataRepository(remote)
            }
            return mInstance!!
        }
    }

    private var mSuggestionCache: MutableMap<Int, Article>? = null // 推荐文章缓存数据，用于下拉加载更多合并数据使用
    private var mSearchCache: MutableMap<Int, Article>? = null // 查询文章缓存数据，用于下拉加载更多合并数据使用
    private var mCategoryCache: MutableMap<Int, Article>? = null // 根据类别获取文章缓存数据，用于下拉加载更多合并数据使用

    override fun listArticle(page: Int, forceUpdate: Boolean, clearCache: Boolean): Observable<MutableList<Article>> {
        /*
        !forceUpdate，不更新，即用户按HOME键再返回APP，此时返回缓存的文章列表
         */
        if (!forceUpdate && mSuggestionCache != null) {
            return Observable.fromIterable(ArrayList(mSuggestionCache?.values))
                    .toSortedList { a1, a2 -> SortDescendUtil.sortArticle(a1, a2) }
                    .toObservable()
        }
        /*
        forceUpdate&&!clearCache，更新且不清缓存，即用户向下滑动列表的情况，此时请求数据并保存缓存
         */
        if (!clearCache && mSuggestionCache != null) {
            val ob1 = Observable.fromIterable(ArrayList(mSuggestionCache!!.values))
                    .toSortedList { a1, a2 -> SortDescendUtil.sortArticle(a1, a2) }
                    .toObservable()
            /*
            请求新的数据
             */
            val ob2 = mRemote.listArticle(page, forceUpdate, clearCache)
                    .doOnNext { refreshSuggestionCache(clearCache, it) }
            /*
            合并数据
             */
            return Observable.merge(ob1, ob2).collect({ mutableListOf<Article>() }, { t1, t2 -> t1.addAll(t2) }).toObservable()
        }
        /*
        forceUpdate && cleanCache，更新且清缓存，即下拉刷新，还有第一次加载的情况
         */
        return mRemote.listArticle(0, forceUpdate, clearCache).doOnNext { refreshSuggestionCache(clearCache, it) }
    }

    override fun queryArticle(page: Int, query: String, forceUpdate: Boolean, clearCache: Boolean): Observable<MutableList<Article>> {
        if (!forceUpdate && mSearchCache != null) {
            return Observable.fromIterable(ArrayList(mSearchCache?.values))
                    .toSortedList { a1, a2 -> SortDescendUtil.sortArticle(a1, a2) }
                    .toObservable()
        }
        if (!clearCache && mSearchCache != null) {
            val ob1 = Observable.fromIterable(ArrayList(mSearchCache?.values))
                    .toSortedList { a1, a2 -> SortDescendUtil.sortArticle(a1, a2) }
                    .toObservable()
            val ob2 = mRemote.queryArticle(page, query, forceUpdate, clearCache)
                    .doOnNext { refreshSearchCache(false, it) }
            return Observable.merge(ob1, ob2).collect({ mutableListOf<Article>() }, { t1, t2 -> t1.addAll(t2) }).toObservable()
        }
        return mRemote.queryArticle(0, query, forceUpdate, clearCache).doOnNext { refreshSearchCache(true, it) }
    }

    override fun categoryArticle(page: Int, categoryId: Int, forceUpdate: Boolean, clearCache: Boolean): Observable<MutableList<Article>> {
        if (!forceUpdate && mCategoryCache != null) {
            return Observable.fromIterable(ArrayList(mCategoryCache?.values))
                    .toSortedList { a1, a2 -> SortDescendUtil.sortArticle(a1, a2) }
                    .toObservable()
        }
        if (!clearCache && mCategoryCache != null) {
            val ob1 = Observable.fromIterable(ArrayList(mCategoryCache?.values))
                    .toSortedList { a1, a2 -> SortDescendUtil.sortArticle(a1, a2) }
                    .toObservable()
            val ob2 = mRemote.categoryArticle(page, categoryId, forceUpdate, clearCache)
                    .doOnNext { refreshCategoryCache(false, it) }
            return Observable.merge(ob1, ob2).collect({ mutableListOf<Article>() }, { t1, t2 -> t1.addAll(t2) }).toObservable()
        }
        return mRemote.categoryArticle(0, categoryId, forceUpdate, clearCache).doOnNext { refreshCategoryCache(true, it) }
    }

    /**
     * 刷新文章缓存
     */
    private fun refreshSuggestionCache(clearCache: Boolean, articleList: List<Article>) {
        if (mSuggestionCache == null) {
            mSuggestionCache = LinkedHashMap()
        }
        if (clearCache) {
            mSuggestionCache?.clear()
        }
        for (article in articleList) {
            mSuggestionCache?.put(article.articleId, article)
        }
    }

    private fun refreshSearchCache(cleanCache: Boolean, articleList: MutableList<Article>) {
        if (mSearchCache == null) {
            mSearchCache = mutableMapOf()
        }
        if (cleanCache) {
            mSearchCache?.clear()
        }
        for (article in articleList) {
            mSearchCache?.put(article.articleId, article)
        }
    }

    private fun refreshCategoryCache(cleanCache: Boolean, articleList: MutableList<Article>) {
        if (mCategoryCache == null) {
            mCategoryCache = mutableMapOf()
        }
        if (cleanCache) {
            mCategoryCache?.clear()
        }
        for (article in articleList) {
            mCategoryCache?.put(article.articleId, article)
        }
    }
}