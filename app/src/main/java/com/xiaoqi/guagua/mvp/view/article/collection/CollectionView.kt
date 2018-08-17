package com.xiaoqi.guagua.mvp.view.article.collection

import com.xiaoqi.guagua.mvp.model.bean.EssayData
import com.xiaoqi.guagua.mvp.presenter.CollectionPresenter
import com.xiaoqi.guagua.mvp.view.BaseView

interface CollectionView : BaseView<CollectionPresenter> {
    /**
     * 视图是否激活
     */
    fun isActive(): Boolean

    fun showCollection(essayList: List<EssayData.Data.Essay>)

    fun showEmptyView(toShow: Boolean)
}