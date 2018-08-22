package com.xiaoqi.guagua.mvp.vp.search

import android.support.v4.widget.NestedScrollView
import android.support.v7.widget.AppCompatTextView
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.view.View
import com.codingending.library.FairySearchView
import com.xiaoqi.guagua.BaseFragment
import com.xiaoqi.guagua.R
import com.xiaoqi.guagua.mvp.model.bean.Article
import com.xiaoqi.guagua.mvp.vp.article.ArticleRecyclerViewAdapter
import com.xiaoqi.guagua.util.ToastUtil

/**
* Created by xujie on 2018/8/20.
*/
class SearchFragment : BaseFragment(), SearchView {




    private lateinit var mTbSearch: Toolbar
    private lateinit var mFsvSearch: FairySearchView
    private lateinit var mNsvSearch: NestedScrollView
    private lateinit var mRvSearch: RecyclerView
    private lateinit var mTvSearchNothing: AppCompatTextView

    private lateinit var mAdapter: ArticleRecyclerViewAdapter

    private lateinit var mPresenter: SearchPresenter

    companion object {
        fun newInstance(): SearchFragment {
            return SearchFragment()
        }
    }

    override fun getResource(): Int {
        return R.layout.fragment_search
    }

    override fun initView(view: View) {
        mTbSearch = view.findViewById(R.id.tb_search)
        mFsvSearch = view.findViewById(R.id.fsv_search)
        mFsvSearch.setOnBackClickListener { activity?.onBackPressed() }
        mFsvSearch.setOnEnterClickListener { queryArticle(mFsvSearch.searchText) }
        mNsvSearch = view.findViewById(R.id.nsv_search)
        mRvSearch = view.findViewById(R.id.rv_search)
        mRvSearch.layoutManager = LinearLayoutManager(context)
        mAdapter = ArticleRecyclerViewAdapter(context, mutableListOf())
        mRvSearch.adapter = mAdapter
        mTvSearchNothing = view.findViewById(R.id.tv_search_nothing)
    }

    private fun queryArticle(query: String) {
        mPresenter.queryArticle(0, query, true, true)
        ToastUtil.showMsg(query)
    }

    override fun setPresenter(presenter: SearchPresenter) {
        mPresenter = presenter
    }

    override fun isActive(): Boolean {
        return isAdded && isResumed
    }

    override fun showArticle(articleList: List<Article>) {
        mAdapter.update(articleList)
    }

    override fun showEmpty(toShow: Boolean) {
        mTvSearchNothing.visibility = if (toShow) View.VISIBLE else View.INVISIBLE
        mNsvSearch.visibility = if (toShow) View.INVISIBLE else View.VISIBLE
    }
}