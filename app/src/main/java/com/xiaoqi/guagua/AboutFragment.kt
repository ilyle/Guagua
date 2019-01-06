package com.xiaoqi.guagua

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.support.v7.widget.AppCompatTextView
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import com.hx.curtain.drawer.CurtainContentLayout
import com.xiaoqi.guagua.util.AppUtil
import com.xiaoqi.guagua.util.AppVersion
import com.xiaoqi.guagua.util.PhoneInformation
import com.xiaoqi.guagua.util.ToastUtil
import com.xiaoqi.litedragview.LiteDragHelper
import com.xiaoqi.liteitemview.LiteItemView

class AboutFragment : BaseFragment(), LiteItemView.OnLiteItemViewClick {

    private lateinit var mTvApp: AppCompatTextView
    private lateinit var mLivUpdate: LiteItemView
    private lateinit var mLivAppreciate: LiteItemView
    private lateinit var mLivLicense: LiteItemView
    private lateinit var mLivSupport: LiteItemView

    private lateinit var mCclAbout: CurtainContentLayout // 可滑动窗帘布局
    private lateinit var mLlConcat: LinearLayout
    private lateinit var mIvConcat: ImageView

    companion object {
        fun newInstance(): AboutFragment {
            return AboutFragment()
        }
    }

    override fun getResource(): Int {
        return R.layout.fragment_about
    }

    override fun initView(view: View) {
        mTvApp = view.findViewById(R.id.tv_about_app)
        mLivUpdate = view.findViewById(R.id.liv_about_update)
        mLivAppreciate = view.findViewById(R.id.liv_about_appreciate)
        mLivLicense = view.findViewById(R.id.liv_about_license)
        mLivSupport = view.findViewById(R.id.liv_about_support)

        mCclAbout = view.findViewById(R.id.ccl_about)
        mIvConcat = view.findViewById(R.id.iv_concat)
        mLlConcat = view.findViewById(R.id.ll_concat)
        mCclAbout.setCurtainLayoutListener { curtainLayout, slideOffset ->
            mIvConcat.rotation = 360 * slideOffset
            mLlConcat.scaleX = slideOffset
            mLlConcat.scaleY = slideOffset
            mLlConcat.translationY = - mLlConcat.left * slideOffset
        }

        LiteDragHelper.bind(context!!, mIvConcat, Color.parseColor("#536DFD"))
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupView()
    }

    private fun setupView() {
        mTvApp.text = StringBuilder(resources.getString(R.string.app_name)).append(AppVersion.versionName)
        mLivUpdate.rightText = PhoneInformation.deviceName
        mLivUpdate.setOnLiteItemViewClick(this)
        mLivAppreciate.setOnLiteItemViewClick(this)
        mLivLicense.setOnLiteItemViewClick(this)
        mLivSupport.setOnLiteItemViewClick(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.liv_about_update -> {
                ToastUtil.showMsg((v as LiteItemView).leftText)
            }
            R.id.liv_about_appreciate -> {
                AppUtil.openInBrowser(context, getString(R.string.about_link_hongyang))
            }
            R.id.liv_about_license -> {
                startActivity(Intent(context, LicenseActivity::class.java))
            }
            R.id.liv_about_support -> {
                AppUtil.openInBrowser(context, getString(R.string.about_link_ilyle))
            }
        }
    }
}