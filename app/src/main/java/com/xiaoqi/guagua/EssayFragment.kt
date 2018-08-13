package com.xiaoqi.guagua

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.NestedScrollView
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.AppCompatTextView
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.xiaoqi.guagua.mvp.model.bean.EssayData
import com.xiaoqi.guagua.mvp.presenter.EssayPresenter
import com.xiaoqi.guagua.mvp.view.EssayView

class EssayFragment : Fragment(), EssayView {

    private var mIsFirstLoad: Boolean = true
    private val index: Int = 0
    private var curPage: Int = 0

    private lateinit var mSrlEssay: SwipeRefreshLayout
    private lateinit var mNsvEssay: NestedScrollView
    private lateinit var mRvEssay: RecyclerView
    private lateinit var mTvEssayNothing: AppCompatTextView

    private lateinit var mAdapter: EssayAdapter

    private lateinit var presenter: EssayPresenter

    companion object {
        fun newInstance(): EssayFragment {
            return EssayFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_essay, container, false)
        initView(view)
        mSrlEssay.setOnRefreshListener { presenter.getEssay(index, true, true) }
        mNsvEssay.setOnScrollChangeListener { nestScrollView: NestedScrollView, _: Int, scrollY: Int, _: Int, _: Int ->
            if (scrollY == (nestScrollView.getChildAt(0).measuredHeight - view.measuredHeight)) {
                loadMore()
            }
        }
        return view
    }

    override fun onResume() {
        super.onResume()
        if (mIsFirstLoad) {
            mIsFirstLoad = false
            presenter.getEssay(index, true, true)
            curPage = index
        } else {
            presenter.getEssay(curPage, false, false)
        }
    }

    override fun initView(view: View) {
        mSrlEssay = view.findViewById(R.id.srl_essay)
        mNsvEssay = view.findViewById(R.id.nsv_essay)
        mRvEssay = view.findViewById(R.id.rv_essay)
        mAdapter = EssayAdapter(context, mutableListOf())
        mRvEssay.adapter = mAdapter
        mRvEssay.layoutManager = LinearLayoutManager(context)
        mTvEssayNothing = view.findViewById(R.id.tv_essay_nothing)
    }

    override fun isActive(): Boolean {
        return isAdded && isResumed
    }

    override fun setLoadingIndicator(isActive: Boolean) {
        mSrlEssay.isRefreshing = isActive
    }

    override fun showEssay(essayList: List<EssayData.Data.Essay>) {
        mAdapter.update(essayList)
        mRvEssay.adapter = mAdapter
    }

    override fun showEmptyView(toShow: Boolean) {
        mTvEssayNothing.visibility = if (toShow) View.VISIBLE else View.INVISIBLE
        mNsvEssay.visibility = if (toShow) View.INVISIBLE else View.VISIBLE
    }

    override fun setPresenter(presenter: EssayPresenter) {
        this.presenter = presenter
    }

    private fun loadMore() {

    }
}