package com.xiaoqi.guagua.mvp.vp.article

import android.graphics.Color
import android.os.Bundle
import android.support.design.widget.AppBarLayout
import android.support.design.widget.CollapsingToolbarLayout
import android.support.design.widget.TabLayout
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.*
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.xiaoqi.guagua.BaseFragment
import com.xiaoqi.guagua.R
import com.xiaoqi.guagua.mvp.model.source.impl.ArticleDataSourceImpl
import com.xiaoqi.guagua.mvp.model.source.impl.CollectionDataSourceImpl
import com.xiaoqi.guagua.mvp.model.source.local.CollectionDataSourceLocal
import com.xiaoqi.guagua.mvp.model.source.remote.ArticleDataSourceRemote
import com.xiaoqi.guagua.mvp.vp.article.collection.CollectionFragment
import com.xiaoqi.guagua.mvp.vp.article.collection.CollectionPresenterImpl
import com.xiaoqi.guagua.mvp.vp.article.suggestion.SuggestionFragment
import com.xiaoqi.guagua.mvp.vp.article.suggestion.SuggestionPresenterImpl
import com.xiaoqi.guagua.mvp.vp.search.SearchActivity

class ArticleFragment : BaseFragment(), AppBarLayout.OnOffsetChangedListener {
    override fun onOffsetChanged(p0: AppBarLayout?, p1: Int) {
    }

    private lateinit var mSuggestionFragment: SuggestionFragment
    private lateinit var mCollectionFragment: CollectionFragment

    private lateinit var mAblArticle: AppBarLayout
    private lateinit var mCtlArticle: CollapsingToolbarLayout
    private lateinit var mTbArticle: Toolbar
    private lateinit var mIvArticle: ImageView
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

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        setHasOptionsMenu(true)
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun getResource(): Int {
        return R.layout.fragment_article
    }

    override fun initView(view: View) {
        mAblArticle = view.findViewById(R.id.abl_article)
        mTbArticle = view.findViewById(R.id.tb_article)
        mCtlArticle = view.findViewById(R.id.ctl_article)
        mIvArticle = view.findViewById(R.id.iv_article)
        mTlArticle = view.findViewById(R.id.tl_article)
        mVpArticle = view.findViewById(R.id.vp_article)
        mAblArticle.addOnOffsetChangedListener(this)
        mCtlArticle.setCollapsedTitleTextColor(Color.WHITE)
        mCtlArticle.setExpandedTitleColor(Color.TRANSPARENT)
        mTbArticle.inflateMenu(R.menu.toolbar_main_menu)
        (activity as AppCompatActivity).setSupportActionBar(mTbArticle)
        Glide.with(context!!).load(R.drawable.banner_article).apply(RequestOptions().diskCacheStrategy(DiskCacheStrategy.RESOURCE)).into(mIvArticle) // 加载Banner
        mVpArticle.adapter = ArticleFragmentPagerAdapter(childFragmentManager, context, mSuggestionFragment, mCollectionFragment)
        mVpArticle.offscreenPageLimit = 2
        mTlArticle.setupWithViewPager(mVpArticle)

    }

    override fun onPause() {
        super.onPause()
        mAblArticle.removeOnOffsetChangedListener(this)
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

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater?.inflate(R.menu.toolbar_main_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.tb_article_menu -> {
                SearchActivity.startActivity(activity!!)
            }
        }
        return true
    }
}