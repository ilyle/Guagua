package com.xiaoqi.guagua.mvp.view

import com.xiaoqi.guagua.mvp.model.bean.EssayData
import com.xiaoqi.guagua.mvp.presenter.EssayPresenter

interface EssayView: BaseView<EssayPresenter> {

    /**
     * 视图是否激活
     */
    fun isActive(): Boolean

    fun setLoadingIndicator(isActive: Boolean)

    fun showEssay(essayList: List<EssayData.Data.Essay>)

    fun showEmptyView(toShow: Boolean)
}