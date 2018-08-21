package com.xiaoqi.guagua.util

import com.xiaoqi.guagua.mvp.model.bean.Article

object SortDescendUtil {
    fun sortArticle(a1: Article, a2: Article): Int {
        return if (a1.publishTime > a1.publishTime) -1 else 1
    }
}