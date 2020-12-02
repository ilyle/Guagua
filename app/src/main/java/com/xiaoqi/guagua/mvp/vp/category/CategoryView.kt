package com.xiaoqi.guagua.mvp.vp.category

import com.xiaoqi.guagua.mvp.model.bean.Category
import com.xiaoqi.guagua.mvp.vp.BaseView

interface CategoryView : BaseView<CategoryPresenter> {

    /**
     * 视图是否激活
     */
    fun isActive(): Boolean

    fun setLoadingIndicator(isRefreshing: Boolean)

    fun showCategory(categoryList: MutableList<Category>)

    fun showEmpty(isEmpty: Boolean)
}