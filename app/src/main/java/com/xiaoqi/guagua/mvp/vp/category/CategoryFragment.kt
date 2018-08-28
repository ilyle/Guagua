package com.xiaoqi.guagua.mvp.vp.category

import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import com.xiaoqi.guagua.BaseFragment
import com.xiaoqi.guagua.R
import com.xiaoqi.guagua.mvp.model.bean.Category

class CategoryFragment: BaseFragment(), CategoryView {

    private lateinit var mSrlCategory: SwipeRefreshLayout
    private lateinit var mRvCategory: RecyclerView
    private lateinit var mTvCategory: TextView

    private lateinit var mPresenter: CategoryPresenter

    companion object {
        fun newInstance(): CategoryFragment {
            return CategoryFragment()
        }
    }

    override fun getResource(): Int {
        return R.layout.fragment_category
    }

    override fun initView(view: View) {
        mSrlCategory = view.findViewById(R.id.srl_category)
        mRvCategory = view.findViewById(R.id.rv_category)
        mTvCategory = view.findViewById(R.id.tv_category_nothing)
    }

    override fun setPresenter(presenter: CategoryPresenter) {
        mPresenter = presenter
    }

    override fun isActive(): Boolean {
        return isAdded && isResumed
    }

    override fun showCategory(articleList: MutableList<Category>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showEmpty(toShow: Boolean) {
        mTvCategory.visibility = if (toShow) View.VISIBLE else View.INVISIBLE
        mRvCategory.visibility = if (toShow) View.INVISIBLE else View.VISIBLE
    }
}