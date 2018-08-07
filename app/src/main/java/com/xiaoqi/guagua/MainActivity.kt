package com.xiaoqi.guagua

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.design.widget.BottomNavigationView.OnNavigationItemSelectedListener
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.view.MenuItem
import android.widget.Toast

class MainActivity : AppCompatActivity(), OnNavigationItemSelectedListener {

    private lateinit var mArticleFragment: ArticleFragment
    private lateinit var mCategoryFragment: CategoryFragment
    private lateinit var mAboutFragment: AboutFragment

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

    private lateinit var mBnv: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initView()
        initFragment(savedInstanceState)
    }

    private fun initView() {
        mBnv = findViewById(R.id.bnv_main)
        mBnv.setOnNavigationItemSelectedListener(this)
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
        if (fragment is ArticleFragment) {
            fragmentManager.beginTransaction().show(mArticleFragment).hide(mCategoryFragment).hide(mAboutFragment).commit()
            setTitle(R.string.nav_bottom_main_article)
        } else if (fragment is CategoryFragment) {
            fragmentManager.beginTransaction().show(mCategoryFragment).hide(mArticleFragment).hide(mAboutFragment).commit()
            setTitle(R.string.nav_bottom_main_category)
        } else if (fragment is AboutFragment) {
            fragmentManager.beginTransaction().show(mAboutFragment).hide(mArticleFragment).hide(mCategoryFragment).commit()
            setTitle(R.string.nav_bottom_main_about)
        }
    }
}
