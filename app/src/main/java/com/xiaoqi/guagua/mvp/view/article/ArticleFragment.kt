package com.xiaoqi.guagua.mvp.view.article

import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.view.ViewPager
import android.view.View
import com.xiaoqi.guagua.BaseFragment
import com.xiaoqi.guagua.R
import com.xiaoqi.guagua.mvp.model.source.impl.CollectionDataSourceImpl
import com.xiaoqi.guagua.mvp.model.source.impl.EssayDataSourceImpl
import com.xiaoqi.guagua.mvp.model.source.local.CollectionDataSourceLocal
import com.xiaoqi.guagua.mvp.model.source.remote.EssayDataSourceRemote
import com.xiaoqi.guagua.mvp.presenter.impl.CollectionPresenterImpl
import com.xiaoqi.guagua.mvp.presenter.impl.EssayPresenterImpl
import com.xiaoqi.guagua.mvp.view.article.collection.CollectionFragment
import com.xiaoqi.guagua.mvp.view.article.essay.EssayFragment

class ArticleFragment: BaseFragment() {

    private lateinit var mEssayFragment: EssayFragment
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
            mEssayFragment = fragmentManager.getFragment(savedInstanceState, EssayFragment::class.java.simpleName) as EssayFragment
            mCollectionFragment = fragmentManager.getFragment(savedInstanceState, CollectionFragment::class.java.simpleName) as CollectionFragment
        } else {
            mEssayFragment = EssayFragment.newInstance()
            mCollectionFragment = CollectionFragment.newInstance()
        }

        /*
        Presenter拥有View和Model实例
         */
        EssayPresenterImpl.build(mEssayFragment, EssayDataSourceImpl.getInstance(EssayDataSourceRemote.getInstance()))
        CollectionPresenterImpl.build(mCollectionFragment, CollectionDataSourceImpl.getInstance(CollectionDataSourceLocal.getInstance()))
    }


    override fun getResource(): Int {
        return R.layout.fragment_article
    }

    override fun initView(view: View) {
        mTlArticle = view.findViewById(R.id.tl_article)
        mVpArticle = view.findViewById(R.id.vp_article)
        mVpArticle.adapter = ArticleAdapter(childFragmentManager, context, mEssayFragment, mCollectionFragment)
        mVpArticle.offscreenPageLimit = 2
        mTlArticle.setupWithViewPager(mVpArticle)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        val fragmentManager = childFragmentManager
        if (mEssayFragment.isAdded) {
            fragmentManager.putFragment(outState, EssayFragment::class.java.simpleName, mEssayFragment)
        }
        if (mCollectionFragment.isAdded) {
            fragmentManager.putFragment(outState, CollectionFragment::class.java.simpleName, mCollectionFragment)
        }
    }

}