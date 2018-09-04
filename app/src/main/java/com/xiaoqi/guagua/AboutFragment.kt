package com.xiaoqi.guagua

import android.support.v7.widget.AppCompatTextView
import android.view.View
import com.xiaoqi.guagua.util.AppUtil
import com.xiaoqi.liteitemview.LiteItemView

class AboutFragment : BaseFragment(), LiteItemView.OnLiteItemViewClick {

    private lateinit var mTvApp: AppCompatTextView
    private lateinit var mLivUpdate: LiteItemView
    private lateinit var mLivAppreciate: LiteItemView

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
        mLivUpdate.rightText = AppUtil.getVersionCode()
    }

    override fun onClick() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}