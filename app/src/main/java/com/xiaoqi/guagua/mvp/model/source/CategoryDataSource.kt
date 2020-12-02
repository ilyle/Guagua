package com.xiaoqi.guagua.mvp.model.source

import com.xiaoqi.guagua.mvp.model.bean.Category
import io.reactivex.Observable

interface CategoryDataSource {

    /**
     * 获取类别列表
     */
    fun listCategory(): Observable<MutableList<Category>>
}