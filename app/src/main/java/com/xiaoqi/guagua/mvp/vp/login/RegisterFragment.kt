package com.xiaoqi.guagua.mvp.vp.login

import android.view.View
import android.widget.TextView
import com.xiaoqi.guagua.BaseFragment
import com.xiaoqi.guagua.R

class RegisterFragment : BaseFragment(), View.OnClickListener {
    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.tv_register_to_login -> {
                (activity as LoginActivity).showLoginFragment()
            }
        }
    }

    private lateinit var mTvToLogin: TextView

    override fun getResource(): Int {
        return R.layout.fragment_register
    }

    override fun initView(view: View) {
        mTvToLogin = view.findViewById(R.id.tv_register_to_login)
        mTvToLogin.setOnClickListener(this)
    }

    companion object {
        fun newInstance(): RegisterFragment {
            return RegisterFragment()
        }
    }
}