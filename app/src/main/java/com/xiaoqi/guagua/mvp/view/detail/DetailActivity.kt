package com.xiaoqi.guagua.mvp.view.detail

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.xiaoqi.guagua.R
import com.xiaoqi.guagua.mvp.model.source.impl.CollectionDataSourceImpl
import com.xiaoqi.guagua.mvp.model.source.local.CollectionDataSourceLocal
import com.xiaoqi.guagua.mvp.presenter.impl.DetailPresenterImpl

class DetailActivity : AppCompatActivity() {

    private lateinit var mDetailFragment: DetailFragment

    companion object {
        const val ARTICLE: String = "Article"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_common)
        mDetailFragment = if (savedInstanceState != null) {
            supportFragmentManager.getFragment(savedInstanceState, DetailFragment::class.java.simpleName) as DetailFragment
        } else {
            DetailFragment.newInstance()
        }
        supportFragmentManager.beginTransaction().replace(R.id.fl_activity_common, mDetailFragment).commit() // 展示DetailFragment

        /*
        构建DetailPresenter，用有view和model实例
         */
        DetailPresenterImpl.build(mDetailFragment, CollectionDataSourceImpl.getInstance(CollectionDataSourceLocal.getInstance()))
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        if (mDetailFragment.isAdded) {
            supportFragmentManager.putFragment(outState, DetailFragment::class.java.simpleName, mDetailFragment)
        }
    }
}
