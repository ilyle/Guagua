package com.xiaoqi.guagua.mvp.vp.category

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import com.scwang.smartrefresh.layout.SmartRefreshLayout
import com.xiaoqi.guagua.BaseFragment
import com.xiaoqi.guagua.R
import com.xiaoqi.guagua.mvp.model.bean.Category
import com.xiaoqi.guagua.mvp.model.source.repository.CategoryDataRepository
import com.xiaoqi.guagua.mvp.model.source.remote.CategoryDataSourceRemote
import com.xiaoqi.guagua.mvp.vp.banner.BannerFragment
import org.greenrobot.eventbus.EventBus

class CategoryFragment : BaseFragment(), CategoryView {

    private var mIsFirstLoad: Boolean = true
    private lateinit var mSrlCategory: SmartRefreshLayout
    private lateinit var mRvCategory: RecyclerView
    private lateinit var mTvCategoryEmpty: TextView
    private lateinit var mAdapter: CategoryRecyclerViewAdapter
    private lateinit var mPresenter: CategoryPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        /*
        构建CategoryPresenter，用有view和model实例
         */
        CategoryPresenterImpl.build(this, CategoryDataRepository.getInstance(CategoryDataSourceRemote.getInstance()))
    }

    override fun getResource(): Int {
        return R.layout.fragment_category
    }

    override fun initView(view: View) {
        mSrlCategory = view.findViewById(R.id.srl_category)
        mSrlCategory.setOnRefreshListener {
            EventBus.getDefault().post(BannerFragment.EVENT_GET_BANNER);
            mPresenter.listCategory()
        }
        mRvCategory = view.findViewById(R.id.rv_category)
        mTvCategoryEmpty = view.findViewById(R.id.tv_category_empty)
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
        mSrlCategory.finishRefresh()
    }

    override fun showCategory(categoryList: MutableList<Category>) {
        mAdapter.update(categoryList)
    }

    override fun showEmpty(isEmpty: Boolean) {
        mTvCategoryEmpty.visibility = if (isEmpty) View.VISIBLE else View.INVISIBLE
        mRvCategory.visibility = if (isEmpty) View.INVISIBLE else View.VISIBLE
    }

    companion object {
        fun newInstance(): CategoryFragment {
            return CategoryFragment()
        }
    }
}