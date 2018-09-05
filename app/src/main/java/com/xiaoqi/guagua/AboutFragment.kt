package com.xiaoqi.guagua

import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.AppCompatTextView
import android.view.View
import com.xiaoqi.guagua.util.AppUtil
import com.xiaoqi.guagua.util.ToastUtil
import com.xiaoqi.liteitemview.LiteItemView

class AboutFragment : BaseFragment(), LiteItemView.OnLiteItemViewClick {

    private lateinit var mTvApp: AppCompatTextView
    private lateinit var mLivUpdate: LiteItemView
    private lateinit var mLivAppreciate: LiteItemView
    private lateinit var mLivLicense: LiteItemView
    private lateinit var mLivSupport: LiteItemView

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
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupView()
    }

    private fun setupView() {
        mTvApp.text = StringBuilder(resources.getString(R.string.app_name)).append(AppUtil.getVersionCode())
        mLivUpdate.rightText = AppUtil.getVersionCode()
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