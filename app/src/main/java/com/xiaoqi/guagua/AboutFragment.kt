package com.xiaoqi.guagua

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

class AboutFragment: BaseFragment() {


    companion object {
        fun newInstance(): AboutFragment {
            return AboutFragment()
        }
    }

    override fun getResource(): Int {
        return R.layout.fragment_about
    }

    override fun initView(view: View) {
    }
}