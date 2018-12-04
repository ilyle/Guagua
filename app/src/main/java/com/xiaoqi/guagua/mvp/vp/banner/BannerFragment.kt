package com.xiaoqi.guagua.mvp.vp.banner

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.xiaoqi.guagua.BaseFragment
import com.xiaoqi.guagua.R
import com.xiaoqi.guagua.mvp.model.source.repository.BannerDataRepository
import com.xiaoqi.guagua.mvp.model.source.remote.BannerDataSourceRemote
import com.xiaoqi.guagua.mvp.vp.detail.DetailActivity
import com.youth.banner.Banner
import com.youth.banner.BannerConfig
import com.youth.banner.Transformer
import com.youth.banner.loader.ImageLoader
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

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
        BannerPresenterImpl.build(this, BannerDataRepository.getInstance(BannerDataSourceRemote.getInstance()))
        /*
        注册EventBus
         */
        EventBus.getDefault().register(this@BannerFragment)
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

    override fun onDestroy() {
        super.onDestroy()
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this)
        }
        mPresenter.unSubscribe()
    }

    /**
     * 接收EventBus事件
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    fun handleEvent(string: String) {
        if (string == EVENT_GET_BANNER) {
            mPresenter.getBanner()
        }
    }

    override fun showBanner(bannerList: List<com.xiaoqi.guagua.mvp.model.bean.Banner>) {
        val imageUrlList = mutableListOf<String>()
        for (banner in bannerList) {
            banner.imagePath?.let { imageUrlList.add(it) }
        }
        mBanner.setImages(imageUrlList)
        mBanner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR)
        mBanner.setBannerAnimation(Transformer.ZoomOutSlide)
        mBanner.setDelayTime(5000)
        mBanner.setImageLoader(object : ImageLoader() {
            override fun displayImage(context: Context?, path: Any?, imageView: ImageView?) {
                Glide.with(context!!).load(path).into(imageView!!)
            }
        })
        mBanner.setOnBannerListener { position ->
            DetailActivity.startAction(context!!, bannerList[position], DetailActivity.TYPE_BANNER)
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
        const val EVENT_GET_BANNER = "EVENT_GET_BANNER"
    }
}