package com.xiaoqi.guagua

import android.content.Context
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter

class ArticleAdapter(manager: FragmentManager, context: Context?, essayFragment: EssayFragment,
                     readLaterFragment: ReadLaterFragment) : FragmentPagerAdapter(manager) {

    private val mCount = 2
    private val mContext = context
    private val mEssayFragment = essayFragment
    private val mReadLaterFragment = readLaterFragment
    private val mTitle: Array<String> = arrayOf(mContext!!.getString(R.string.tl_article_essay), mContext.getString(R.string.tl_article_read_later))

    override fun getCount(): Int {
        return mCount
    }

    override fun getItem(p0: Int): Fragment {
        var fragment = Fragment()
        when (p0) {
            0 -> fragment = mEssayFragment
            1 -> fragment = mReadLaterFragment
        }
        return fragment
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return mTitle[position]
    }
}