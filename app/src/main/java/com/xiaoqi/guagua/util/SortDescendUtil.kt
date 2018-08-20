package com.xiaoqi.guagua.util

import com.xiaoqi.guagua.mvp.model.bean.EssayData.Data.Essay

object SortDescendUtil {
    fun sortEssay(e1: Essay, e2: Essay): Int {
        return if (e1.publishTime > e2.publishTime) -1 else 1
    }
}