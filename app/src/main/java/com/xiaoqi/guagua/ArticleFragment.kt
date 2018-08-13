package com.xiaoqi.guagua

import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.xiaoqi.guagua.mvp.model.source.impl.EssayDataSourceImpl
import com.xiaoqi.guagua.mvp.model.source.remote.EssayDataSourceRemote
import com.xiaoqi.guagua.mvp.presenter.EssayPresenter
import com.xiaoqi.guagua.mvp.presenter.impl.EssayPresenterImpl

class ArticleFragment: Fragment() {

    private lateinit var mEssayFragment: EssayFragment
    private lateinit var mReadLaterFragment: ReadLaterFragment

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
            mReadLaterFragment = fragmentManager.getFragment(savedInstanceState, ReadLaterFragment::class.java.simpleName) as ReadLaterFragment
        } else {
            mEssayFragment = EssayFragment.newInstance()
            mReadLaterFragment = ReadLaterFragment.newInstance()
        }

        EssayPresenterImpl(mEssayFragment, EssayDataSourceImpl.getInstance(EssayDataSourceRemote.instance))
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        val fragmentManager = childFragmentManager
        if (mEssayFragment.isAdded) {
            fragmentManager.putFragment(outState, EssayFragment::class.java.simpleName, mEssayFragment)
        }
        if (mReadLaterFragment.isAdded) {
            fragmentManager.putFragment(outState, ReadLaterFragment::class.java.simpleName, mReadLaterFragment)
        }
    }

    private fun initView(view: View) {
        mTlArticle = view.findViewById(R.id.tl_article)
        mVpArticle = view.findViewById(R.id.vp_article)
        mVpArticle.adapter = ArticleAdapter(childFragmentManager, context, mEssayFragment, mReadLaterFragment)
        mVpArticle.offscreenPageLimit = 2
        mTlArticle.setupWithViewPager(mVpArticle)
    }
}