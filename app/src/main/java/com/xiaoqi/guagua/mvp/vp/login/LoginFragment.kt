package com.xiaoqi.guagua.mvp.vp.login

import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.xiaoqi.guagua.BaseFragment
import com.xiaoqi.guagua.MainActivity
import com.xiaoqi.guagua.R
import com.xiaoqi.guagua.mvp.model.bean.User
import com.xiaoqi.guagua.util.MmkvUtil
import com.xiaoqi.guagua.util.ToastUtil

class LoginFragment : BaseFragment(), View.OnClickListener, LoginView {


    private lateinit var mPresenter: UserPresenter
    private lateinit var mEtUsername: EditText
    private lateinit var mEtPassword: EditText
    private lateinit var mBtnLogin: Button
    private lateinit var mTvToRegister: TextView

    override fun setPresenter(presenter: UserPresenter) {
        mPresenter = presenter
    }

    override fun isActive(): Boolean {
        return isAdded && isResumed
    }

    override fun loginSuccess(user: User) {
        ToastUtil.showMsg(getString(R.string.login_success))
        saveUser(user)
    }

    private fun saveUser(user: User) {
        MmkvUtil.setUserId(user.id)
        MmkvUtil.setUserName(user.username)
        MmkvUtil.setUserPw(user.password)
        MmkvUtil.setUserIsLogin(true)
        navigate2Main()
    }

    private fun navigate2Main() {
        MainActivity.startAction(context!!)
    }

    override fun showLoginFail(errorMsg: String) {
        ToastUtil.showMsg(errorMsg)
    }

    override fun showNetworkError(errorMsg: String) {
        ToastUtil.showMsg(errorMsg)
    }

    override fun getResource(): Int {
        return R.layout.fragment_login
    }

    override fun initView(view: View) {
        mEtUsername = view.findViewById(R.id.et_login_username)
        mEtPassword = view.findViewById(R.id.et_login_password)
        mBtnLogin = view.findViewById(R.id.btn_login)
        mTvToRegister = view.findViewById(R.id.tv_login_to_register)

        mBtnLogin.setOnClickListener(this)
        mTvToRegister.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_login -> {
                val username = mEtUsername.text.trim().toString()
                val password = mEtPassword.text.trim().toString()
                mPresenter.login(username, password)
            }
            R.id.tv_login_to_register -> {
                (activity as LoginActivity).showRegisterFragment()
            }
        }
    }

    companion object {
        fun newInstance(): LoginFragment {
            return LoginFragment()
        }
    }
}