package com.xiaoqi.guagua.mvp.vp.category

import android.view.View
import com.xiaoqi.guagua.BaseFragment
import com.xiaoqi.guagua.R

class CategoryFragment: BaseFragment() {


    companion object {
        fun newInstance(): CategoryFragment {
            return CategoryFragment()
        }
    }

    override fun getResource(): Int {
        return R.layout.fragment_category
    }

    override fun initView(view: View) {
    }
}