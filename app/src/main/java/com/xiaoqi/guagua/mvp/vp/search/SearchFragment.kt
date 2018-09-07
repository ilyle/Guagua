package com.xiaoqi.guagua.mvp.vp.search

import android.support.v4.widget.NestedScrollView
import android.support.v7.widget.AppCompatTextView
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.text.TextUtils
import android.view.View
import android.widget.EditText
import android.widget.Toast
import com.codingending.library.FairySearchView
import com.xiaoqi.guagua.BaseFragment
import com.xiaoqi.guagua.R
import com.xiaoqi.guagua.mvp.model.bean.Article
import com.xiaoqi.guagua.mvp.vp.article.ArticleRecyclerViewAdapter
import com.xiaoqi.guagua.util.NetWorkUtil

/**
* Created by xujie on 2018/8/20.
*/
class SearchFragment : BaseFragment(), SearchView {

    private lateinit var mTbSearch: Toolbar
    private lateinit var mFsvSearch: FairySearchView
    private lateinit var mNsvSearch: NestedScrollView
    private lateinit var mRvSearch: RecyclerView
    private lateinit var mTvSearchEmpty: AppCompatTextView
    private lateinit var mAdapter: ArticleRecyclerViewAdapter

    private lateinit var mPresenter: SearchPresenter

    private var mSearchCurPage: Int = 0 // 查询文章当前页
    private var mCategoryCurPage: Int = 0 // 根据类别获取文章当前页
    private var mMode = MODE_SEARCH

    private var mCategoryId: Int? = null

    companion object {

        private const val MODE_SEARCH = 0
        private const val MODE_CATEGORY = 1

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
        mFsvSearch.setOnEnterClickListener {
            mMode = MODE_SEARCH // 搜索模式
            mSearchCurPage = 0
            queryArticle(mSearchCurPage, mFsvSearch.searchText)
        }

        /*
        反射获取private成员searchEditText
         */
        val field = FairySearchView::class.java.getDeclaredField("searchEditText")
        field.isAccessible = true
        val et =  field.get(mFsvSearch) as EditText

        mNsvSearch = view.findViewById(R.id.nsv_search)
        mNsvSearch.setOnScrollChangeListener { nestScrollView: NestedScrollView, _: Int, scrollY: Int, _: Int, _: Int ->
            /*
            nestScrollView只有一个子View，nestScrollView.getChildAt(0)获取的是RecyclerView，根据布局R.layout.fragment_suggestion定义
             */
            if (scrollY == (nestScrollView.getChildAt(0).measuredHeight - mNsvSearch.measuredHeight)) {
                if (mMode == MODE_SEARCH) {
                    loadQueryArticleMore(++mSearchCurPage, mFsvSearch.searchText)
                } else if (mMode == MODE_CATEGORY) {
                    loadCategoryArticleMore(++mCategoryCurPage, mCategoryId!!)
                }
            }
        }
        mRvSearch = view.findViewById(R.id.rv_search)
        mRvSearch.layoutManager = LinearLayoutManager(context)
        mAdapter = ArticleRecyclerViewAdapter(context, mutableListOf())
        mRvSearch.adapter = mAdapter
        mTvSearchEmpty = view.findViewById(R.id.tv_search_empty)

        /*
        根据CategoryFragment传进的分类id展示问斩
         */
        val intent = activity?.intent
        val query = intent?.getStringExtra(SearchActivity.QUERY)
        mCategoryId = intent?.getIntExtra(SearchActivity.CATEGORY_ID, 0)
        if (!TextUtils.isEmpty(query)) {
            mMode = MODE_CATEGORY // 类别获取文章模式
            mCategoryCurPage = 0
            mFsvSearch.searchText = query
            et.setSelection(mFsvSearch.searchText.length) // 设置光标位置
            categoryArticle(mCategoryCurPage, mCategoryId!!)
        }
    }

    /**
     * 根据搜索查询文章
     */
    private fun queryArticle(page: Int, query: String) {
        mPresenter.queryArticle(page, query, true, true)
    }

    /**
     * 根据类别获取文章
     */
    private fun categoryArticle(page: Int, categoryId: Int) {
        mPresenter.categoryArticle(page, categoryId,true, true)
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

    override fun showEmpty(isEmpty: Boolean) {
        mTvSearchEmpty.visibility = if (isEmpty) View.VISIBLE else View.INVISIBLE
        mNsvSearch.visibility = if (isEmpty) View.INVISIBLE else View.VISIBLE
    }

    private fun loadQueryArticleMore(page: Int, query: String) {
        if (NetWorkUtil.isNetWorkAvailable(context!!)) {
            mPresenter.queryArticle(page, query, true, false)
        } else {
            Toast.makeText(context!!, R.string.toast_network_unavailable, Toast.LENGTH_SHORT).show()
        }
    }

    private fun loadCategoryArticleMore(page: Int, categoryId: Int) {
        if (NetWorkUtil.isNetWorkAvailable(context!!)) {
            mPresenter.categoryArticle(page, categoryId, true, false)
        } else {
            Toast.makeText(context!!, R.string.toast_network_unavailable, Toast.LENGTH_SHORT).show()
        }
    }
}