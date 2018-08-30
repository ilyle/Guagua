package com.xiaoqi.guagua.mvp.vp.article.suggestion

import android.support.v4.content.ContextCompat
import android.support.v4.widget.NestedScrollView
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.AppCompatTextView
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.Toast
import com.xiaoqi.guagua.BaseFragment
import com.xiaoqi.guagua.R
import com.xiaoqi.guagua.mvp.model.bean.Article
import com.xiaoqi.guagua.mvp.vp.article.ArticleRecyclerViewAdapter
import com.xiaoqi.guagua.util.NetWorkUtil

class SuggestionFragment : BaseFragment(), SuggestionView {

    private var mIsFirstLoad: Boolean = true
    private var curPage: Int = 0

    private lateinit var mSrlSuggestion: SwipeRefreshLayout
    private lateinit var mNsvSuggestion: NestedScrollView
    private lateinit var mRvSuggestion: RecyclerView
    private lateinit var mTvSuggestionEmpty: AppCompatTextView

    private lateinit var mAdapter: ArticleRecyclerViewAdapter

    private lateinit var mPresenter: SuggestionPresenter

    companion object {
        fun newInstance(): SuggestionFragment {
            return SuggestionFragment()
        }
    }

    override fun getResource(): Int {
        return R.layout.fragment_suggestion
    }

    override fun initView(view: View) {
        mSrlSuggestion = view.findViewById(R.id.srl_suggestion)
        mNsvSuggestion = view.findViewById(R.id.nsv_suggestion)
        mRvSuggestion = view.findViewById(R.id.rv_suggestion)
        mAdapter = ArticleRecyclerViewAdapter(context, mutableListOf())
        mRvSuggestion.adapter = mAdapter
        mRvSuggestion.layoutManager = LinearLayoutManager(context)
        mTvSuggestionEmpty = view.findViewById(R.id.tv_suggestion_empty)
        mSrlSuggestion.setColorSchemeColors(ContextCompat.getColor(context!!, R.color.color_primary))
        mSrlSuggestion.setOnRefreshListener { curPage = 0; mPresenter.listArticle(0, true, true) }
        mNsvSuggestion.setOnScrollChangeListener { nestScrollView: NestedScrollView, _: Int, scrollY: Int, _: Int, _: Int ->
            /*
            nestScrollView只有一个子View，nestScrollView.getChildAt(0)获取的是RecyclerView，根据布局R.layout.fragment_suggestion定义
             */
            if (scrollY == (nestScrollView.getChildAt(0).measuredHeight - view.measuredHeight)) {
                loadMore(++curPage)
            }
        }
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


    override fun isActive(): Boolean {
        return isAdded && isResumed
    }

    override fun setLoadingIndicator(isRefreshing: Boolean) {
        mSrlSuggestion.isRefreshing = isRefreshing
    }

    override fun showArticle(articleList: List<Article>) {
        mAdapter.update(articleList)
    }

    override fun showEmpty(toShow: Boolean) {
        mTvSuggestionEmpty.visibility = if (toShow) View.VISIBLE else View.INVISIBLE
        mNsvSuggestion.visibility = if (toShow) View.INVISIBLE else View.VISIBLE
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
}