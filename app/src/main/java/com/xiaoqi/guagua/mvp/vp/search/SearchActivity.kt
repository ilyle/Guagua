package com.xiaoqi.guagua.mvp.vp.search

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.xiaoqi.guagua.R
import com.xiaoqi.guagua.mvp.model.source.impl.ArticleDataSourceImpl
import com.xiaoqi.guagua.mvp.model.source.remote.ArticleDataSourceRemote

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
        SearchPresenterImpl.build(mSearchFragment, ArticleDataSourceImpl.getInstance(ArticleDataSourceRemote.getInstance()))
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        if (mSearchFragment.isAdded) {
            supportFragmentManager.putFragment(outState, SearchFragment::class.java.simpleName, mSearchFragment)
        }
    }
}