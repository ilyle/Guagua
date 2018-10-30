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
    private lateinit var mMineFragment: MineFragment
    private lateinit var mRegisterFragment: RegisterFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_common)
        savedInstanceState?.let {
            mLoginFragment = supportFragmentManager.getFragment(it, LoginFragment::class.java.simpleName) as LoginFragment
            mMineFragment = supportFragmentManager.getFragment(it, MineFragment::class.java.simpleName) as MineFragment
            mRegisterFragment = supportFragmentManager.getFragment(it, RegisterFragment::class.java.simpleName) as RegisterFragment
        }
        if (savedInstanceState == null) {
            mLoginFragment = LoginFragment.newInstance()
            mMineFragment = MineFragment.newInstance()
            mRegisterFragment = RegisterFragment.newInstance()
        }
        if (!mLoginFragment.isAdded) {
            supportFragmentManager.beginTransaction().add(R.id.fl_activity_common, mLoginFragment, LoginFragment::class.java.simpleName).commit()
        }
        if (!mMineFragment.isAdded) {
            supportFragmentManager.beginTransaction().add(R.id.fl_activity_common, mMineFragment, MineFragment::class.java.simpleName).commit()
        }
        if (!mRegisterFragment.isAdded) {
            supportFragmentManager.beginTransaction().add(R.id.fl_activity_common, mRegisterFragment, RegisterFragment::class.java.simpleName).commit()
        }
        /*
        构建BannerPresenter实例，拥有view和model对象，同时在BannerPresenterImpl的init函数中，将BannerPresenter实例传给view（BannerFragment）
         */
        UserPresenterImpl.build(mLoginFragment, mMineFragment, UserDataSourceImpl.getInstance(UserDataSourceRemote.getInstance()))
        intent?.let {
            val loginType = it.getIntExtra(LoginActivity.TYPE, LoginActivity.TYPE_LOGIN)
            when (loginType) {
                LoginActivity.TYPE_LOGIN -> {
                    showLoginFragment()
                }
                LoginActivity.TYPE_LOGOUT -> {
                    showMineFragment()
                }
            }
        }
    }

    fun showLoginFragment() {
        supportFragmentManager.beginTransaction().setCustomAnimations(R.anim.right_in, R.anim.right_out).show(mLoginFragment).hide(mMineFragment).hide(mRegisterFragment).commit()
    }

    fun showMineFragment() {
        supportFragmentManager.beginTransaction().show(mMineFragment).hide(mLoginFragment).hide(mRegisterFragment).commit()
    }

    fun showRegisterFragment() {
        supportFragmentManager.beginTransaction().setCustomAnimations(R.anim.left_in, R.anim.left_out).show(mRegisterFragment).hide(mLoginFragment).hide(mMineFragment).commit()
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        outState?.let {
            if (mLoginFragment.isAdded) {
                supportFragmentManager.putFragment(it, LoginFragment::class.java.simpleName, mLoginFragment)
            }
            if (mLoginFragment.isAdded) {
                supportFragmentManager.putFragment(it, MineFragment::class.java.simpleName, mMineFragment)
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
