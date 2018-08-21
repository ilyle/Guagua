package com.xiaoqi.guagua.mvp.view

import android.view.View
import com.xiaoqi.guagua.BaseFragment
import com.xiaoqi.guagua.R

/**
 * Created by xujie on 2018/8/20.
 */
class SearchFragment : BaseFragment() {

    companion object {
        fun newInstance(): SearchFragment {
            return SearchFragment()
        }
    }

    override fun getResource(): Int {
        return R.layout.fragment_search
    }

    override fun initView(view: View) {
    }
}