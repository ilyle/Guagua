package com.xiaoqi.guagua.mvp.view.article.essay

import com.xiaoqi.guagua.mvp.model.bean.Essay
import com.xiaoqi.guagua.mvp.presenter.EssayPresenter
import com.xiaoqi.guagua.mvp.view.BaseView

interface EssayView: BaseView<EssayPresenter> {

    /**
     * 视图是否激活
     */
    fun isActive(): Boolean

    fun setLoadingIndicator(isActive: Boolean)

    fun showEssay(essayList: List<Essay>)

    fun showEmptyView(toShow: Boolean)
}