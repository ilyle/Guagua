package com.xiaoqi.guagua.mvp.vp.login

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.xiaoqi.guagua.R
import com.xiaoqi.guagua.mvp.model.source.impl.UserDataSourceImpl
import com.xiaoqi.guagua.mvp.model.source.remote.UserDataSourceRemote


class LoginActivity : AppCompatActivity() {
    private lateinit var mLoginFragment: LoginFragment
    private lateinit var mLogoutFragment: LogoutFragment
    private lateinit var mRegisterFragment: RegisterFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_common)
        savedInstanceState?.let {
            mLoginFragment = supportFragmentManager.getFragment(it, LoginFragment::class.java.simpleName) as LoginFragment
            mLogoutFragment = supportFragmentManager.getFragment(it, LogoutFragment::class.java.simpleName) as LogoutFragment
            mRegisterFragment = supportFragmentManager.getFragment(it, RegisterFragment::class.java.simpleName) as RegisterFragment
        }
        if (savedInstanceState == null) {
            mLoginFragment = LoginFragment.newInstance()
            mLogoutFragment = LogoutFragment.newInstance()
            mRegisterFragment = RegisterFragment.newInstance()
        }
        if (!mLoginFragment.isAdded) {
            supportFragmentManager.beginTransaction().add(R.id.fl_activity_common, mLoginFragment, LoginFragment::class.java.simpleName).commit()
        }
        if (!mLogoutFragment.isAdded) {
            supportFragmentManager.beginTransaction().add(R.id.fl_activity_common, mLogoutFragment, LogoutFragment::class.java.simpleName).commit()
        }
        if (!mRegisterFragment.isAdded) {
            supportFragmentManager.beginTransaction().add(R.id.fl_activity_common, mRegisterFragment, RegisterFragment::class.java.simpleName).commit()
        }
        /*
        构建BannerPresenter实例，拥有view和model对象，同时在BannerPresenterImpl的init函数中，将BannerPresenter实例传给view（BannerFragment）
         */
        UserPresenterImpl.build(mLoginFragment, mLogoutFragment, UserDataSourceImpl.getInstance(UserDataSourceRemote.getInstance()))
        intent?.let {
            val loginType = it.getIntExtra(LoginActivity.TYPE, LoginActivity.TYPE_LOGIN)
            when (loginType) {
                LoginActivity.TYPE_LOGIN -> {
                    showLoginFragment()
                }
                LoginActivity.TYPE_LOGOUT -> {
                    showLogoutFragment()
                }
            }
        }
    }

    fun showLoginFragment() {
        supportFragmentManager.beginTransaction().setCustomAnimations(R.anim.right_in, R.anim.right_out).show(mLoginFragment).hide(mLogoutFragment).hide(mRegisterFragment).commit()
    }

    fun showLogoutFragment() {
        supportFragmentManager.beginTransaction().show(mLogoutFragment).hide(mLoginFragment).hide(mRegisterFragment).commit()
    }

    fun showRegisterFragment() {
        supportFragmentManager.beginTransaction().setCustomAnimations(R.anim.left_in, R.anim.left_out).show(mRegisterFragment).hide(mLoginFragment).hide(mLogoutFragment).commit()
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        outState?.let {
            if (mLoginFragment.isAdded) {
                supportFragmentManager.putFragment(it, LoginFragment::class.java.simpleName, mLoginFragment)
            }
            if (mLoginFragment.isAdded) {
                supportFragmentManager.putFragment(it, LogoutFragment::class.java.simpleName, mLogoutFragment)
            }
            if (mRegisterFragment.isAdded) {
                supportFragmentManager.putFragment(it, RegisterFragment::class.java.simpleName, mRegisterFragment)
            }
        }
    }

    companion object {

        const val TYPE = "type"
        const val TYPE_LOGIN = 1
        const val TYPE_LOGOUT = 0

        fun startAction(context: Context, type: Int) {
            val intent = Intent(context, LoginActivity::class.java)
            intent.putExtra(LoginActivity.TYPE, type)
            context.startActivity(intent)
        }
    }
}
