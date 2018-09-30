package com.xiaoqi.guagua.mvp.vp.search

import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.support.v7.widget.AppCompatTextView
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.codingending.library.FairySearchView
import com.scwang.smartrefresh.layout.SmartRefreshLayout
import com.wanglu.lib.WPopup
import com.wanglu.lib.WPopupModel
import com.xiaoqi.guagua.BaseFragment
import com.xiaoqi.guagua.R
import com.xiaoqi.guagua.mvp.model.bean.Article
import com.xiaoqi.guagua.mvp.vp.article.ArticleRecyclerViewAdapter
import com.xiaoqi.guagua.util.MmkvUtil
import com.xiaoqi.guagua.util.NetWorkUtil

/**
 * Created by xujie on 2018/8/20.
 * 搜索
 */
class SearchFragment : BaseFragment(), SearchView {

    private lateinit var mClSearch: ConstraintLayout
    private lateinit var mFsvSearch: FairySearchView
    private lateinit var mEtSearch: EditText
    private lateinit var mSrlSearch: SmartRefreshLayout
    private lateinit var mRvSearch: RecyclerView
    private lateinit var mTvSearchEmpty: AppCompatTextView
    private lateinit var mClSearchHistory: ConstraintLayout
    private lateinit var mRvSearchHistory: RecyclerView
    private lateinit var mTvSearchHistoryClean: TextView

    private lateinit var mArticleAdapter: ArticleRecyclerViewAdapter
    private lateinit var mSearchHistoryAdapter: SearchHistoryAdapter
    private lateinit var mSearchHistoryClickListener: SearchHistoryAdapter.SearchHistoryClickListener
    private lateinit var mSearchHistoryLongClickListener: SearchHistoryAdapter.SearchHistoryLongClickListener
    private lateinit var mPresenter: SearchPresenter

    private var mSearchCurPage: Int = 0 // 查询文章当前页
    private var mCategoryCurPage: Int = 0 // 根据类别获取文章当前页
    private var mMode = MODE_SEARCH
    private var mCategoryId: Int? = null

    override fun getResource(): Int {
        return R.layout.fragment_search
    }

    override fun initView(view: View) {
        mClSearch = view.findViewById(R.id.cl_search)
        mFsvSearch = view.findViewById(R.id.fsv_search)
        /*
        反射获取private成员searchEditText
         */
        val field = FairySearchView::class.java.getDeclaredField("searchEditText")
        field.isAccessible = true
        mEtSearch = field.get(mFsvSearch) as EditText

        mSrlSearch = view.findViewById(R.id.srl_search)
        mRvSearch = view.findViewById(R.id.rv_search)
        mTvSearchEmpty = view.findViewById(R.id.tv_search_empty)
        mClSearchHistory = view.findViewById(R.id.cl_search_history)
        mRvSearchHistory = view.findViewById(R.id.rv_search_history)
        mTvSearchHistoryClean = view.findViewById(R.id.tv_search_history_clean)
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
            mEtSearch.setSelection(mFsvSearch.searchText.length) // 设置光标位置
            categoryArticle(mCategoryCurPage, mCategoryId!!)
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupUI()
    }

    /**
     * 控件设置
     */
    private fun setupUI() {

        mSearchHistoryClickListener = object : SearchHistoryAdapter.SearchHistoryClickListener {
            override fun onSearchHistoryClickListener(search: String) {
                onSearchEnterClick(search)
            }
        }

        mSearchHistoryLongClickListener = object : SearchHistoryAdapter.SearchHistoryLongClickListener {
            override fun onSearchHistoryLongClickListener(view: View, search: String, position: Int): Boolean {
                showDeleteWindow(view, search, position)
                return true
            }
        }

        mArticleAdapter = ArticleRecyclerViewAdapter(context, mutableListOf())
        mSearchHistoryAdapter = SearchHistoryAdapter(context!!, mutableListOf(), mSearchHistoryClickListener, mSearchHistoryLongClickListener)
        /*
         * 搜索历史相关
         */
        if (mMode == MODE_SEARCH) {
            mClSearch.visibility = View.GONE
            mClSearchHistory.visibility = View.VISIBLE
            val searchHistoryList = MmkvUtil.getSearchHistory()
            mTvSearchHistoryClean.visibility = if (searchHistoryList.isEmpty()) View.GONE else View.VISIBLE
            mRvSearchHistory.layoutManager = LinearLayoutManager(context)
            mSearchHistoryAdapter.updateData(searchHistoryList)
            mRvSearchHistory.adapter = mSearchHistoryAdapter
        }
        /*
        清除历史
         */
        mTvSearchHistoryClean.setOnClickListener {
            MmkvUtil.cleanSearchHistory()
            mSearchHistoryAdapter.updateData(MmkvUtil.getSearchHistory())
            mTvSearchHistoryClean.visibility = View.GONE
        }

        /*
         * 搜索框相关
         */
        mFsvSearch.setOnBackClickListener { activity?.onBackPressed() }
        mFsvSearch.setOnEnterClickListener { onSearchEnterClick(it) }

        mEtSearch.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                /*
                搜索框为空的时候
                 */
                if (s.toString().isEmpty()) {
                    val searchHistoryList = MmkvUtil.getSearchHistory()
                    mSearchHistoryAdapter.updateData(searchHistoryList)
                    mTvSearchHistoryClean.visibility = if (searchHistoryList.isEmpty()) View.GONE else View.VISIBLE
                    mClSearch.visibility = View.GONE
                    mClSearchHistory.visibility = View.VISIBLE
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

        })
        /*
        加载更多相关
         */
        mSrlSearch.setOnLoadmoreListener {
            if (mMode == MODE_SEARCH) {
                loadQueryArticleMore(++mSearchCurPage, mFsvSearch.searchText)
            } else if (mMode == MODE_CATEGORY) {
                loadCategoryArticleMore(++mCategoryCurPage, mCategoryId!!)
            }
        }

        mRvSearch.layoutManager = LinearLayoutManager(context)
        mRvSearch.adapter = mArticleAdapter


    }

    private fun showDeleteWindow(view: View, search: String, position: Int) {
        /*
        设置WPopup
         */
        val wPopup = WPopup.Builder(activity!!)
                .setData(listOf(WPopupModel(getString(R.string.tv_delete))))
                .setPopupOrientation(WPopup.Builder.VERTICAL)
                .setCancelable(true)
                .setClickView(mRvSearchHistory) // 点击的View，如果是RV/LV，则只需要传入RV/LV
                .setOnItemClickListener(object : WPopup.Builder.OnItemClickListener {
                    override fun onItemClick(view: View, position: Int) {
                        MmkvUtil.cleanSearchHistory(search)
                        mSearchHistoryAdapter.updateData(MmkvUtil.getSearchHistory())
                    }
                }).create()
        wPopup.showAtView(view)
    }

    /**
     * 搜索
     */
    private fun onSearchEnterClick(search: String) {
        mMode = MODE_SEARCH // 搜索模式
        mClSearch.visibility = View.VISIBLE
        mClSearchHistory.visibility = View.GONE
        mEtSearch.setText(search)
        mEtSearch.setSelection(mEtSearch.text.length)
        mSearchCurPage = 0
        queryArticle(mSearchCurPage, search)
        MmkvUtil.setSearchHistory(search) // 保存搜索历史
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
        mPresenter.categoryArticle(page, categoryId, true, true)
    }

    override fun setPresenter(presenter: SearchPresenter) {
        mPresenter = presenter
    }

    override fun isActive(): Boolean {
        return isAdded && isResumed
    }

    override fun showArticle(articleList: List<Article>) {
        mSrlSearch.finishLoadmore() // 停在加载动画
        mArticleAdapter.update(articleList)
    }

    override fun showEmpty(isEmpty: Boolean) {
        mTvSearchEmpty.visibility = if (isEmpty) View.VISIBLE else View.GONE
        mRvSearch.visibility = if (isEmpty) View.GONE else View.VISIBLE
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

    companion object {

        private const val MODE_SEARCH = 0
        private const val MODE_CATEGORY = 1

        fun newInstance(): SearchFragment {
            return SearchFragment()
        }
    }
}