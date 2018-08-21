package com.xiaoqi.guagua

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.design.widget.BottomNavigationView.OnNavigationItemSelectedListener
import android.support.design.widget.NavigationView
import android.support.v4.app.Fragment
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import com.xiaoqi.guagua.mvp.vp.search.SearchActivity
import com.xiaoqi.guagua.mvp.vp.article.ArticleFragment

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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.tb_article_menu -> {
                startActivity(Intent(this@MainActivity, SearchActivity::class.java))
            }
        }
        return true
    }

    private lateinit var mDlMain: DrawerLayout
    private lateinit var mTbMain: Toolbar
    private lateinit var mNvMain: NavigationView
    private lateinit var mBnvMain: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initView()
        initFragment(savedInstanceState)
    }

    private fun initView() {
        mDlMain = findViewById(R.id.dl_main)
        mTbMain = findViewById(R.id.tb_main)
        mNvMain = findViewById(R.id.nv_main)
        mBnvMain = findViewById(R.id.bnv_main)
        setSupportActionBar(mTbMain)
        val toggle = ActionBarDrawerToggle(this, mDlMain, mTbMain, R.string.nav_open, R.string.nav_close)
        mDlMain.addDrawerListener(toggle)
        toggle.syncState()
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
}
