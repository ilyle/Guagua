package com.xiaoqi.guagua

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.design.widget.BottomNavigationView.OnNavigationItemSelectedListener
import android.support.design.widget.NavigationView
import android.support.v4.app.Fragment
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import com.xiaoqi.guagua.mvp.vp.article.ArticleFragment
import com.xiaoqi.guagua.mvp.vp.category.CategoryFragment
import com.xiaoqi.guagua.util.ToastUtil

class MainActivity : AppCompatActivity(), OnNavigationItemSelectedListener {

    private var mBackPressTimeStamp: Long = 0
    private lateinit var mArticleFragment: ArticleFragment
    private lateinit var mCategoryFragment: CategoryFragment
    private lateinit var mAboutFragment: AboutFragment
    private lateinit var mDlMain: DrawerLayout
    private lateinit var mNvMain: NavigationView
    private lateinit var mBnvMain: BottomNavigationView

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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initView()
        initFragment(savedInstanceState)
    }

    private fun initView() {
        mDlMain = findViewById(R.id.dl_main)
        mNvMain = findViewById(R.id.nv_main)
        mBnvMain = findViewById(R.id.bnv_main)
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
}
