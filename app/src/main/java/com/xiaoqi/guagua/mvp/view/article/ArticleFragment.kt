package com.xiaoqi.guagua.mvp.view.article

import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.view.ViewPager
import android.view.View
import com.xiaoqi.guagua.BaseFragment
import com.xiaoqi.guagua.R
import com.xiaoqi.guagua.mvp.model.source.impl.CollectionDataSourceImpl
import com.xiaoqi.guagua.mvp.model.source.impl.ArticleDataSourceImpl
import com.xiaoqi.guagua.mvp.model.source.local.CollectionDataSourceLocal
import com.xiaoqi.guagua.mvp.model.source.remote.ArticleDataSourceRemote
import com.xiaoqi.guagua.mvp.presenter.impl.CollectionPresenterImpl
import com.xiaoqi.guagua.mvp.presenter.impl.SuggestionPresenterImpl
import com.xiaoqi.guagua.mvp.view.article.collection.CollectionFragment
import com.xiaoqi.guagua.mvp.view.article.suggestion.SuggestionFragment

class ArticleFragment: BaseFragment() {

    private lateinit var mSuggestionFragment: SuggestionFragment
    private lateinit var mCollectionFragment: CollectionFragment

    private lateinit var mTlArticle: TabLayout
    private lateinit var mVpArticle: ViewPager

    companion object {
        fun newInstance(): ArticleFragment {
            return ArticleFragment()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState != null) {
            val fragmentManager = childFragmentManager
            mSuggestionFragment = fragmentManager.getFragment(savedInstanceState, SuggestionFragment::class.java.simpleName) as SuggestionFragment
            mCollectionFragment = fragmentManager.getFragment(savedInstanceState, CollectionFragment::class.java.simpleName) as CollectionFragment
        } else {
            mSuggestionFragment = SuggestionFragment.newInstance()
            mCollectionFragment = CollectionFragment.newInstance()
        }

        /*
        Presenter拥有View和Model实例
         */
        SuggestionPresenterImpl.build(mSuggestionFragment, ArticleDataSourceImpl.getInstance(ArticleDataSourceRemote.getInstance()))
        CollectionPresenterImpl.build(mCollectionFragment, CollectionDataSourceImpl.getInstance(CollectionDataSourceLocal.getInstance()))
    }


    override fun getResource(): Int {
        return R.layout.fragment_article
    }

    override fun initView(view: View) {
        mTlArticle = view.findViewById(R.id.tl_article)
        mVpArticle = view.findViewById(R.id.vp_article)
        mVpArticle.adapter = ArticleFragmentPagerAdapter(childFragmentManager, context, mSuggestionFragment, mCollectionFragment)
        mVpArticle.offscreenPageLimit = 2
        mTlArticle.setupWithViewPager(mVpArticle)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        val fragmentManager = childFragmentManager
        if (mSuggestionFragment.isAdded) {
            fragmentManager.putFragment(outState, SuggestionFragment::class.java.simpleName, mSuggestionFragment)
        }
        if (mCollectionFragment.isAdded) {
            fragmentManager.putFragment(outState, CollectionFragment::class.java.simpleName, mCollectionFragment)
        }
    }

}