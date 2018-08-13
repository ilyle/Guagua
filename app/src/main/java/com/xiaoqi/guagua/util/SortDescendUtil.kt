package com.xiaoqi.guagua.util

import com.xiaoqi.guagua.mvp.model.bean.EssayData

object SortDescendUtil {
    fun sortEssay(e1: EssayData.Data.Essay, e2: EssayData.Data.Essay): Int {
        return if (e1.publishTime > e2.publishTime) -1 else 1
    }
}