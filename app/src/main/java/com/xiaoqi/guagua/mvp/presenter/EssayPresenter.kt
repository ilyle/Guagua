package com.xiaoqi.guagua.mvp.presenter

interface EssayPresenter : BasePresenter {
    fun getEssay(page: Int, forceUpdate: Boolean, cleanCache: Boolean)
}