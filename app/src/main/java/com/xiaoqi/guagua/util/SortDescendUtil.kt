package com.xiaoqi.guagua.util

import com.xiaoqi.guagua.mvp.model.bean.Article
import com.xiaoqi.guagua.mvp.model.bean.Category

object SortDescendUtil {
    fun sortArticle(a1: Article, a2: Article): Int {
        return if (a1.publishTime > a2.publishTime) -1 else 1
    }

    fun sortCategory(c1: Category, c2: Category): Int {
        return if (c1.order > c2.order) 1 else -1
    }
}