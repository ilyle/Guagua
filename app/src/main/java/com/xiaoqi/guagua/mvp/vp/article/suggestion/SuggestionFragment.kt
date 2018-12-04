package com.xiaoqi.guagua.mvp.vp.article.suggestion

import android.support.v7.widget.AppCompatTextView
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.Toast
import com.scwang.smartrefresh.layout.SmartRefreshLayout
import com.xiaoqi.guagua.BaseFragment
import com.xiaoqi.guagua.R
import com.xiaoqi.guagua.mvp.model.bean.Article
import com.xiaoqi.guagua.mvp.vp.article.ArticleRecyclerViewAdapter
import com.xiaoqi.guagua.util.NetWorkUtil

class SuggestionFragment : BaseFragment(), SuggestionView {

    private var mIsFirstLoad: Boolean = true
    private var curPage: Int = 0
    private lateinit var mSrlSuggestion: SmartRefreshLayout // 第三方下拉刷新、上滑加载控件
    private lateinit var mRvSuggestion: RecyclerView
    private lateinit var mTvSuggestionEmpty: AppCompatTextView
    private lateinit var mAdapter: ArticleRecyclerViewAdapter
    private lateinit var mPresenter: SuggestionPresenter

    override fun getResource(): Int {
        return R.layout.fragment_suggestion
    }

    override fun initView(view: View) {
        mSrlSuggestion = view.findViewById(R.id.srl_suggestion)
        mRvSuggestion = view.findViewById(R.id.rv_suggestion)
        mAdapter = ArticleRecyclerViewAdapter(context, mutableListOf())
        mRvSuggestion.adapter = mAdapter
        mRvSuggestion.layoutManager = LinearLayoutManager(context)
        mTvSuggestionEmpty = view.findViewById(R.id.tv_suggestion_empty)
        /*
        下拉刷新
         */
        mSrlSuggestion.setOnRefreshListener { curPage = 0; mPresenter.listArticle(0, true, true) }
        /*
        上滑加载
         */
        mSrlSuggestion.setOnLoadmoreListener { loadMore(++curPage) }
    }

    override fun onResume() {
        super.onResume()
        if (mIsFirstLoad) {
            mIsFirstLoad = false
            mPresenter.listArticle(0, true, true)
            curPage = 0
        } else {
            mPresenter.listArticle(curPage, false, false)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenter.unSubscribe()
    }

    override fun isActive(): Boolean {
        return isAdded && isResumed
    }

    override fun setLoadingIndicator(isRefreshing: Boolean) {
        if (!isRefreshing) {
            mSrlSuggestion.finishRefresh() // 停止刷新
            mSrlSuggestion.finishLoadmore() // 停止加载
        }
    }

    override fun showArticle(articleList: List<Article>) {
        mAdapter.update(articleList)
    }

    override fun showEmpty(isEmpty: Boolean) {
        mTvSuggestionEmpty.visibility = if (isEmpty) View.VISIBLE else View.INVISIBLE
        mRvSuggestion.visibility = if (isEmpty) View.INVISIBLE else View.VISIBLE
    }

    override fun setPresenter(presenter: SuggestionPresenter) {
        this.mPresenter = presenter
    }

    private fun loadMore(page: Int) {
        if (NetWorkUtil.isNetWorkAvailable(context!!)) {
            mPresenter.listArticle(page, true, false)
        } else {
            Toast.makeText(context!!, R.string.toast_network_unavailable, Toast.LENGTH_SHORT).show()
        }
    }

    companion object {
        fun newInstance(): SuggestionFragment {
            return SuggestionFragment()
        }
    }
}