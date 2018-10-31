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
import com.xiaoqi.guagua.util.ToastUtil
import com.xiaoqi.guagua.util.Validator

class RegisterFragment : BaseFragment(), RegisterView, View.OnClickListener {

    private lateinit var mPresenter: UserPresenter
    private lateinit var mEtUsername: EditText
    private lateinit var mEtPassword: EditText
    private lateinit var mEtPasswordAgain: EditText
    private lateinit var mIvPassword: AppCompatImageView
    private lateinit var mIvPasswordAgain: AppCompatImageView
    private lateinit var mTilPassword: TextInputLayout
    private lateinit var mTilPasswordAgain: TextInputLayout
    private lateinit var mBtnRegister: Button
    private lateinit var mTvToLogin: TextView
    private var isPasswordVisible = false
    private var isPasswordAgainVisible = false
    override fun isActive(): Boolean {
        return isAdded && isResumed
    }

    override fun registerSuccess(user: User) {
        ToastUtil.showMsg(getString(R.string.register_success))
        (activity as LoginActivity).showLoginFragment()
    }

    override fun showRegisterFail(errorMsg: String) {
        ToastUtil.showMsg(errorMsg)
    }

    override fun showNetworkError(errorMsg: String) {
        ToastUtil.showMsg(errorMsg)
    }

    override fun setPresenter(presenter: UserPresenter) {
        mPresenter = presenter
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.iv_register_password -> {
                isPasswordVisible = !isPasswordVisible
                if (!isPasswordVisible) {
                    mIvPassword.setColorFilter(Color.parseColor("#536DFD"))
                    mEtPassword.transformationMethod = HideReturnsTransformationMethod.getInstance()
                } else {
                    mIvPassword.setColorFilter(Color.parseColor("#A5A5A5"))
                    mEtPassword.transformationMethod = PasswordTransformationMethod.getInstance()
                }
                mEtPassword.text?.let { mEtPassword.setSelection(it.length) }
            }
            R.id.iv_register_password_again -> {
                isPasswordAgainVisible = !isPasswordAgainVisible
                if (!isPasswordAgainVisible) {
                    mIvPasswordAgain.setColorFilter(Color.parseColor("#536DFD"))
                    mEtPasswordAgain.transformationMethod = HideReturnsTransformationMethod.getInstance()
                } else {
                    mIvPasswordAgain.setColorFilter(Color.parseColor("#A5A5A5"))
                    mEtPasswordAgain.transformationMethod = PasswordTransformationMethod.getInstance()
                }
                mEtPasswordAgain.text?.let { mEtPasswordAgain.setSelection(it.length) }
            }

            R.id.tv_register_to_login -> {
                (activity as LoginActivity).showLoginFragment()
            }
            R.id.btn_register -> {
                val username = mEtUsername.text?.toString()?.trim()
                val password = mEtPassword.text?.toString()?.trim()
                val passwordAgain = mEtPasswordAgain.text?.toString()?.trim()
                if (checkValidate(username, password, passwordAgain)) {
                    mPresenter.register(username!!, Md5Util.encode(password!!))
                }
            }
        }
    }

    private fun checkValidate(username: String?, password: String?, passwordAgain: String?): Boolean {
        var isValidate = false
        if (TextUtils.isEmpty(username) || !Validator.isUsernameValidate(username!!)) { // 用户名为空或用户名不匹配正则
            ToastUtil.showMsg(getString(R.string.register_username_invalidate))
        } else if (TextUtils.isEmpty(password) || !Validator.isPasswordValidate(password!!)) { // 密码为空或者密码不匹配正则
            ToastUtil.showMsg(getString(R.string.register_password_invalidate))
        } else if (TextUtils.isEmpty(passwordAgain) || password != passwordAgain) { // passwordAgain为空或者两次密码不一致
            ToastUtil.showMsg(getString(R.string.register_password_different))
        } else {
            isValidate = true
        }
        return isValidate
    }

    override fun getResource(): Int {
        return R.layout.fragment_register
    }

    override fun initView(view: View) {
        mEtUsername = view.findViewById(R.id.et_register_username)
        mEtPassword = view.findViewById(R.id.et_register_password)
        mEtPasswordAgain = view.findViewById(R.id.et_register_password_again)
        mIvPassword = view.findViewById(R.id.iv_register_password)
        mIvPasswordAgain = view.findViewById(R.id.iv_register_password_again)
        mTilPassword = view.findViewById(R.id.til_register_password)
        mTilPassword.orientation = LinearLayout.HORIZONTAL
        mTilPasswordAgain = view.findViewById(R.id.til_register_password_again)
        mTilPasswordAgain.orientation = LinearLayout.HORIZONTAL
        mTvToLogin = view.findViewById(R.id.tv_register_to_login)
        mBtnRegister = view.findViewById(R.id.btn_register)

        mIvPassword.setOnClickListener(this)
        mIvPasswordAgain.setOnClickListener(this)
        mBtnRegister.setOnClickListener(this)
        mTvToLogin.setOnClickListener(this)
    }

    companion object {
        fun newInstance(): RegisterFragment {
            return RegisterFragment()
        }
    }
}