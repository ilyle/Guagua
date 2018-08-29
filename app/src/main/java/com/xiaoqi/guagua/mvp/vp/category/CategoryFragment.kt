package com.xiaoqi.guagua.mvp.vp.category

import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import com.xiaoqi.guagua.BaseFragment
import com.xiaoqi.guagua.R
import com.xiaoqi.guagua.mvp.model.bean.Category
import com.xiaoqi.guagua.mvp.model.source.impl.CategoryDataSourceImpl
import com.xiaoqi.guagua.mvp.model.source.remote.CategoryDataSourceRemote

class CategoryFragment: BaseFragment(), CategoryView {

    private var mIsFirstLoad: Boolean = true

    private lateinit var mSrlCategory: SwipeRefreshLayout
    private lateinit var mRvCategory: RecyclerView
    private lateinit var mTvCategory: TextView

    private lateinit var mAdapter: CategoryRecyclerViewAdapter

    private lateinit var mPresenter: CategoryPresenter

    companion object {
        fun newInstance(): CategoryFragment {
            return CategoryFragment()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        /*
        构建CategoryPresenter，用有view和model实例
         */
        CategoryPresenterImpl.build(this, CategoryDataSourceImpl.getInstance(CategoryDataSourceRemote.getInstance()))
    }

    override fun getResource(): Int {
        return R.layout.fragment_category
    }

    override fun initView(view: View) {
        mSrlCategory = view.findViewById(R.id.srl_category)
        mSrlCategory.setColorSchemeColors(ContextCompat.getColor(context!!, R.color.color_primary))
        mSrlCategory.setOnRefreshListener {
            mPresenter.listCategory()
        }
        mRvCategory = view.findViewById(R.id.rv_category)
        mTvCategory = view.findViewById(R.id.tv_category_nothing)
        mAdapter = CategoryRecyclerViewAdapter(context, mutableListOf())
        mRvCategory.layoutManager = LinearLayoutManager(context)
        mRvCategory.adapter = mAdapter
    }

    override fun onResume() {
        super.onResume()
        if (mIsFirstLoad) {
            mIsFirstLoad = false
            mPresenter.listCategory()
        } else {
        }
    }

    override fun setPresenter(presenter: CategoryPresenter) {
        mPresenter = presenter
    }

    override fun isActive(): Boolean {
        return isAdded && isResumed
    }

    override fun setLoadingIndicator(isRefreshing: Boolean) {
        mSrlCategory.isRefreshing = isRefreshing
    }

    override fun showCategory(categoryList: MutableList<Category>) {
        mAdapter.update(categoryList)
    }

    override fun showEmpty(toShow: Boolean) {
        mTvCategory.visibility = if (toShow) View.VISIBLE else View.INVISIBLE
        mRvCategory.visibility = if (toShow) View.INVISIBLE else View.VISIBLE
    }
}