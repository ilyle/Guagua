package com.xiaoqi.guagua.mvp.vp.search

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.xiaoqi.guagua.R

/**
* Created by xujie on 2018/8/20.
*/

class SearchActivity : AppCompatActivity() {

    private lateinit var mSearchFragment: SearchFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_common)
        mSearchFragment = if (savedInstanceState != null) {
            supportFragmentManager.getFragment(savedInstanceState, SearchFragment::class.java.simpleName) as SearchFragment
        } else {
            SearchFragment.newInstance()
        }
        supportFragmentManager.beginTransaction().replace(R.id.fl_activity_common, mSearchFragment).commit()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        if (mSearchFragment.isAdded) {
            supportFragmentManager.putFragment(outState, SearchFragment::class.java.simpleName, mSearchFragment)
        }
    }
}