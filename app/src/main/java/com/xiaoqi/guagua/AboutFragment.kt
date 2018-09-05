package com.xiaoqi.guagua

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
        mLivUpdate.rightText = AppUtil.getVersionCode()

        mLivUpdate.setOnLiteItemViewClick(this)
        mLivAppreciate.setOnLiteItemViewClick(this)
        mLivLicense.setOnLiteItemViewClick(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.liv_about_update -> {ToastUtil.showMsg((v as LiteItemView).leftText)}
            R.id.liv_about_appreciate -> {ToastUtil.showMsg((v as LiteItemView).leftText)}
            R.id.liv_about_license -> {ToastUtil.showMsg((v as LiteItemView).leftText)}
        }
    }
}