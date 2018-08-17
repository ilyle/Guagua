package com.xiaoqi.guagua.mvp.view.article

import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.xiaoqi.guagua.mvp.view.article.essay.EssayFragment
import com.xiaoqi.guagua.R
import com.xiaoqi.guagua.mvp.view.article.collection.CollectionFragment
import com.xiaoqi.guagua.mvp.model.source.impl.EssayDataSourceImpl
import com.xiaoqi.guagua.mvp.model.source.remote.EssayDataSourceRemote
import com.xiaoqi.guagua.mvp.presenter.impl.EssayPresenterImpl

class ArticleFragment: Fragment() {

    private lateinit var mEssayFragment: EssayFragment
    private lateinit var mCollectionFragment: CollectionFragment

    private lateinit var mTlArticle: TabLayout
    private lateinit var mVpArticle: ViewPager

    companion object {
        fun newInstance(): ArticleFragment {
            return ArticleFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_article, container, false)
        initView(view)
        return view
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

    private fun initView(view: View) {
        mTlArticle = view.findViewById(R.id.tl_article)
        mVpArticle = view.findViewById(R.id.vp_article)
        mVpArticle.adapter = ArticleAdapter(childFragmentManager, context, mEssayFragment, mCollectionFragment)
        mVpArticle.offscreenPageLimit = 2
        mTlArticle.setupWithViewPager(mVpArticle)
    }
}