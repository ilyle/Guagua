package com.xiaoqi.guagua

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.design.widget.BottomNavigationView.OnNavigationItemSelectedListener
import android.support.design.widget.NavigationView
import android.support.v4.app.Fragment
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.xiaoqi.guagua.mvp.model.bean.UserInfo
import com.xiaoqi.guagua.mvp.vp.article.ArticleFragment
import com.xiaoqi.guagua.mvp.vp.category.CategoryFragment
import com.xiaoqi.guagua.mvp.vp.login.LoginActivity
import com.xiaoqi.guagua.util.ToastUtil

class MainActivity : AppCompatActivity(), View.OnClickListener, OnNavigationItemSelectedListener {

    private var mBackPressTimeStamp: Long = 0
    private lateinit var mArticleFragment: ArticleFragment
    private lateinit var mCategoryFragment: CategoryFragment
    private lateinit var mAboutFragment: AboutFragment
    private lateinit var mDlMain: DrawerLayout
    private lateinit var mNvMain: NavigationView
    private lateinit var mBnvMain: BottomNavigationView

    private lateinit var mIvUserAvatar: ImageView
    private lateinit var mTvUserAvatar: TextView
    private lateinit var mTvUserNickname: TextView

    override fun onNavigationItemSelected(p0: MenuItem): Boolean {
        when (p0.itemId) {
            R.id.nav_article -> {
                showFragment(mArticleFragment)
            }
            R.id.nav_category -> {
                showFragment(mCategoryFragment)
            }
            R.id.nav_about -> {
                showFragment(mAboutFragment)
            }
        }
        return true
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.civ_nav_header_avatar -> {
                if (UserInfo.user.id == -1) {
                    LoginActivity.startAction(this@MainActivity, LoginActivity.TYPE_LOGIN)
                } else {
                    LoginActivity.startAction(this@MainActivity, LoginActivity.TYPE_LOGOUT)
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initView()
        initFragment(savedInstanceState)
    }

    override fun onResume() {
        super.onResume()
        if (UserInfo.user.id == -1) {
            mTvUserAvatar.text = ""
            mTvUserNickname.text = ""
        } else {
            mTvUserAvatar.text = UserInfo.user.username.substring(0, 1)
            mTvUserNickname.text = UserInfo.user.username
        }
    }

    private fun initView() {
        mDlMain = findViewById(R.id.dl_main)
        mNvMain = findViewById(R.id.nv_main)
        mBnvMain = findViewById(R.id.bnv_main)
        mIvUserAvatar = mNvMain.getHeaderView(0).findViewById(R.id.civ_nav_header_avatar)
        mTvUserAvatar = mNvMain.getHeaderView(0).findViewById(R.id.tv_nav_header_avatar)
        mTvUserNickname = mNvMain.getHeaderView(0).findViewById(R.id.tv_nav_header_nickname)
        mIvUserAvatar.setOnClickListener(this)
        mBnvMain.setOnNavigationItemSelectedListener(this)



    }

    private fun initFragment(savedInstanceState: Bundle?) {
        val fragmentManager = supportFragmentManager
        if (savedInstanceState != null) {
            mArticleFragment = fragmentManager.getFragment(savedInstanceState, ArticleFragment::class.java.simpleName) as ArticleFragment
            mCategoryFragment = fragmentManager.getFragment(savedInstanceState, CategoryFragment::class.java.simpleName) as CategoryFragment
            mAboutFragment = fragmentManager.getFragment(savedInstanceState, AboutFragment::class.java.simpleName) as AboutFragment
        } else {
            mArticleFragment = ArticleFragment.newInstance()
            mCategoryFragment = CategoryFragment.newInstance()
            mAboutFragment = AboutFragment.newInstance()
        }
        if (!mArticleFragment.isAdded) {
            fragmentManager.beginTransaction().add(R.id.fl_main, mArticleFragment, ArticleFragment::class.java.simpleName).commit()
        }
        if (!mCategoryFragment.isAdded) {
            fragmentManager.beginTransaction().add(R.id.fl_main, mCategoryFragment, CategoryFragment::class.java.simpleName).commit()
        }
        if (!mAboutFragment.isAdded) {
            fragmentManager.beginTransaction().add(R.id.fl_main, mAboutFragment, AboutFragment::class.java.simpleName).commit()
        }
        showFragment(mArticleFragment)
    }

    private fun showFragment(fragment: Fragment) {
        val fragmentManager = supportFragmentManager
        when (fragment) {
            is ArticleFragment -> {
                fragmentManager.beginTransaction().show(mArticleFragment).hide(mCategoryFragment).hide(mAboutFragment).commit()
                supportActionBar?.setTitle(R.string.nav_bottom_main_article)
            }
            is CategoryFragment -> {
                fragmentManager.beginTransaction().show(mCategoryFragment).hide(mArticleFragment).hide(mAboutFragment).commit()
                supportActionBar?.setTitle(R.string.nav_bottom_main_category)
            }
            is AboutFragment -> {
                fragmentManager.beginTransaction().show(mAboutFragment).hide(mArticleFragment).hide(mCategoryFragment).commit()
                supportActionBar?.setTitle(R.string.nav_bottom_main_about)
            }
        }
    }

    override fun onBackPressed() {
        doubleClickToExit()
    }

    private fun doubleClickToExit() {
        ToastUtil.showMsg(getString(R.string.toast_press_more) + getString(R.string.app_name))
        val ts = mBackPressTimeStamp
        mBackPressTimeStamp = System.currentTimeMillis()
        if (mBackPressTimeStamp - ts < 2000) {
            finish()
            System.exit(0)
        }
    }

    companion object {
        fun startAction(context: Context) {
            context.startActivity(Intent(context, MainActivity::class.java))
        }
    }
}
