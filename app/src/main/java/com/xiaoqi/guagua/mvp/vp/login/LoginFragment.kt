package com.xiaoqi.guagua.mvp.vp.login

import android.graphics.Color
import android.support.design.widget.TextInputLayout
import android.support.v7.widget.AppCompatImageView
import android.text.TextUtils
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import com.xiaoqi.guagua.BaseFragment
import com.xiaoqi.guagua.R
import com.xiaoqi.guagua.mvp.model.bean.User
import com.xiaoqi.guagua.util.Md5Util
import com.xiaoqi.guagua.util.PreferenceUtil
import com.xiaoqi.guagua.util.ToastUtil

class LoginFragment : BaseFragment(), View.OnClickListener, LoginView {


    private lateinit var mPresenter: UserPresenter
    private lateinit var mEtUsername: EditText
    private lateinit var mTilPassword: TextInputLayout
    private lateinit var mEtPassword: EditText
    private lateinit var mIvPassword: AppCompatImageView // 切换密码是否可见
    private lateinit var mBtnLogin: Button
    private lateinit var mTvToRegister: TextView

    private var isPasswordVisible = false

    override fun setPresenter(presenter: UserPresenter) {
        mPresenter = presenter
    }

    override fun isActive(): Boolean {
        return isAdded && isResumed
    }

    override fun loginSuccess(user: User) {
        ToastUtil.showMsg(getString(R.string.login_success))
        PreferenceUtil.setUser(user) // 保存用户信息
        navigate2Main()
    }

    private fun navigate2Main() {
        activity?.onBackPressed()
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
        mTilPassword = view.findViewById(R.id.til_login_password)
        mEtPassword = view.findViewById(R.id.et_login_password)
        mIvPassword = view.findViewById(R.id.iv_login_password)
        mBtnLogin = view.findViewById(R.id.btn_login)
        mTvToRegister = view.findViewById(R.id.tv_login_to_register)

        mTilPassword.orientation = LinearLayout.HORIZONTAL // 设置LinearLayout布局方向为横向

        mIvPassword.setOnClickListener(this)
        mBtnLogin.setOnClickListener(this)
        mTvToRegister.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.iv_login_password -> {
                isPasswordVisible = !isPasswordVisible
                if (!isPasswordVisible) { // 密码不可见
                    mIvPassword.setColorFilter(Color.parseColor("#536DFD"))
                    mEtPassword.transformationMethod = HideReturnsTransformationMethod.getInstance()
                } else { // 密码可见
                    mIvPassword.setColorFilter(Color.parseColor("#A5A5A5"))
                    mEtPassword.transformationMethod = PasswordTransformationMethod.getInstance()
                }
                mEtPassword.text?.let { mEtPassword.setSelection(it.length) }
            }
            R.id.btn_login -> {
                val username = mEtUsername.text.trim().toString()
                val password = mEtPassword.text.trim().toString()
                if (TextUtils.isEmpty(username)) {
                    ToastUtil.showMsg(getString(R.string.login_no_username))
                } else if (TextUtils.isEmpty(password)) {
                    ToastUtil.showMsg(getString(R.string.login_no_password))
                } else {
                    mPresenter.login(username, Md5Util.encode(password))
                }
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