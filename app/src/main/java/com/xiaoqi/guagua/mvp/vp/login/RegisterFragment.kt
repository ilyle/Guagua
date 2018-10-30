package com.xiaoqi.guagua.mvp.vp.login

import android.view.View
import android.widget.EditText
import android.widget.TextView
import com.xiaoqi.guagua.BaseFragment
import com.xiaoqi.guagua.R

class RegisterFragment : BaseFragment(), View.OnClickListener {

    private lateinit var mEtUsername: EditText
    private lateinit var mEtPassword: EditText
    private lateinit var mEtPasswordAgain: EditText
    private lateinit var mTvToLogin: TextView


    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.tv_register_to_login -> {
                (activity as LoginActivity).showLoginFragment()
            }
        }
    }

    override fun getResource(): Int {
        return R.layout.fragment_register
    }

    override fun initView(view: View) {
        mEtUsername = view.findViewById(R.id.et_register_username)
        mEtPassword = view.findViewById(R.id.et_register_password)
        mEtPasswordAgain = view.findViewById(R.id.et_register_password_again)
        mTvToLogin = view.findViewById(R.id.tv_register_to_login)
        mTvToLogin.setOnClickListener(this)
    }

    companion object {
        fun newInstance(): RegisterFragment {
            return RegisterFragment()
        }
    }
}