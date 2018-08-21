package com.xiaoqi.guagua.mvp.model.source

import android.support.annotation.NonNull
import com.xiaoqi.guagua.mvp.model.bean.Article
import io.reactivex.Observable

interface ArticleDataSource {

    /**
     * 列出文章
     */
    fun listArticle(@NonNull page: Int, @NonNull forceUpdate: Boolean, @NonNull clearCache: Boolean): Observable<MutableList<Article>>

    /**
     * 查询文章
     */
    fun queryArticle(@NonNull page: Int, @NonNull query: String, @NonNull forceUpdate: Boolean, @NonNull clearCache: Boolean): Observable<MutableList<Article>>
}