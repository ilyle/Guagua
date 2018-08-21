package com.xiaoqi.guagua

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

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