package com.xiaoqi.guagua.mvp.vp.banner

import com.xiaoqi.guagua.mvp.model.bean.Banner
import com.xiaoqi.guagua.mvp.vp.BaseView

interface BannerView : BaseView<BannerPresenter> {

    fun showBanner(bannerList: List<Banner>)

    fun hideBanner()
}