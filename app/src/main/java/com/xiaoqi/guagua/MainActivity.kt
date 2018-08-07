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

    private lateinit var articleFragment: ArticleFragment
    private lateinit var categoryFragment: CategoryFragment
    private lateinit var aboutFragment: AboutFragment

    override fun onNavigationItemSelected(p0: MenuItem): Boolean {
        when (p0.itemId) {
            R.id.nav_article -> {
                showFragment(articleFragment)
            }
            R.id.nav_category -> {
                showFragment(categoryFragment)
            }
            R.id.nav_about -> {
                showFragment(aboutFragment)
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
            articleFragment = fragmentManager.getFragment(savedInstanceState, ArticleFragment::class.java.simpleName) as ArticleFragment
            categoryFragment = fragmentManager.getFragment(savedInstanceState, CategoryFragment::class.java.simpleName) as CategoryFragment
            aboutFragment = fragmentManager.getFragment(savedInstanceState, AboutFragment::class.java.simpleName) as AboutFragment
        } else {
            articleFragment = ArticleFragment.newInstance()
            categoryFragment = CategoryFragment.newInstance()
            aboutFragment = AboutFragment.newInstance()
        }
        if (!articleFragment.isAdded) {
            fragmentManager.beginTransaction().add(R.id.fl_main, articleFragment, ArticleFragment::class.java.simpleName).commit()
        }
        if (!categoryFragment.isAdded) {
            fragmentManager.beginTransaction().add(R.id.fl_main, categoryFragment, CategoryFragment::class.java.simpleName).commit()
        }
        if (!aboutFragment.isAdded) {
            fragmentManager.beginTransaction().add(R.id.fl_main, aboutFragment, AboutFragment::class.java.simpleName).commit()
        }
        showFragment(articleFragment)
    }

    private fun showFragment(fragment: Fragment) {
        val fragmentManager = supportFragmentManager
        if (fragment is ArticleFragment) {
            fragmentManager.beginTransaction().show(articleFragment).hide(categoryFragment).hide(aboutFragment).commit()
            setTitle(R.string.nav_bottom_main_article)
        } else if (fragment is CategoryFragment) {
            fragmentManager.beginTransaction().show(categoryFragment).hide(articleFragment).hide(aboutFragment).commit()
            setTitle(R.string.nav_bottom_main_category)
        } else if (fragment is AboutFragment) {
            fragmentManager.beginTransaction().show(aboutFragment).hide(articleFragment).hide(categoryFragment).commit()
            setTitle(R.string.nav_bottom_main_about)
        }
    }
}
