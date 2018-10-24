package com.xiaoqi.guagua.mvp.vp.banner

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.xiaoqi.guagua.BaseFragment
import com.xiaoqi.guagua.R
import com.xiaoqi.guagua.mvp.model.source.impl.BannerDataSourceImpl
import com.xiaoqi.guagua.mvp.model.source.remote.BannerDataSourceRemote
import com.youth.banner.Banner
import com.youth.banner.BannerConfig
import com.youth.banner.Transformer
import com.youth.banner.loader.ImageLoader

class BannerFragment : BaseFragment(), BannerView {

    private var mIsFirstLoad: Boolean = true
    private lateinit var mBanner: Banner
    private lateinit var mPresenter: BannerPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.i(TAG, "onCreate")
        /*
        构建BannerPresenter实例，拥有view和model对象，同时在BannerPresenterImpl的init函数中，将BannerPresenter实例传给view（BannerFragment）
         */
        BannerPresenterImpl.build(this, BannerDataSourceImpl.getInstance(BannerDataSourceRemote.getInstance()))
    }

    override fun getResource(): Int {
        return R.layout.fragment_banner
    }

    override fun initView(view: View) {
        mBanner = view.findViewById(R.id.banner)
    }

    override fun onResume() {
        super.onResume()
        Log.i(TAG, "onResume")
        if (mIsFirstLoad) {
            mIsFirstLoad = false
            mPresenter.getBanner()
        }
        mBanner.startAutoPlay()
    }

    override fun onStop() {
        super.onStop()
        mBanner.stopAutoPlay()
    }

    override fun showBanner(bannerList: List<com.xiaoqi.guagua.mvp.model.bean.Banner>) {
        val imageUrlList = mutableListOf<String>()
        for (banner in bannerList) {
            imageUrlList.add(banner.imagePath)
        }
        mBanner.setImages(imageUrlList)
        mBanner.setBannerStyle(BannerConfig.NUM_INDICATOR)
        mBanner.setBannerAnimation(Transformer.ZoomOutSlide)
        mBanner.setDelayTime(5000)
        mBanner.setImageLoader(object : ImageLoader(){
            override fun displayImage(context: Context?, path: Any?, imageView: ImageView?) {
                Glide.with(context!!).load(path).into(imageView!!)
            }
        })
        mBanner.setOnBannerListener {

        }
        mBanner.start()
    }

    override fun hideBanner() {
        mBanner.visibility = View.GONE
    }

    override fun setPresenter(presenter: BannerPresenter) {
        mPresenter = presenter
    }

    companion object {
        const val TAG = "BannerFragment"
    }
}