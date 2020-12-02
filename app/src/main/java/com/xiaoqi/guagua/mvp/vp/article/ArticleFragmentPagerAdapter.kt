package com.xiaoqi.guagua.mvp.vp.article

import android.content.Context
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.xiaoqi.guagua.mvp.vp.article.suggestion.SuggestionFragment
import com.xiaoqi.guagua.R
import com.xiaoqi.guagua.mvp.vp.article.collection.CollectionFragment

class ArticleFragmentPagerAdapter(manager: FragmentManager, context: Context?, suggestionFragment: SuggestionFragment,
                                  collectionFragment: CollectionFragment) : FragmentPagerAdapter(manager) {

    private val mCount = 2
    private val mContext = context
    private val mSuggestionFragment = suggestionFragment
    private val mCollectionFragment = collectionFragment
    private val mTitle: Array<String> = arrayOf(mContext!!.getString(R.string.tl_article_suggestion), mContext.getString(R.string.tl_article_collection))

    override fun getCount(): Int {
        return mCount
    }

    override fun getItem(p0: Int): Fragment {
        var fragment = Fragment()
        when (p0) {
            0 -> fragment = mSuggestionFragment
            1 -> fragment = mCollectionFragment
        }
        return fragment
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return mTitle[position]
    }
}