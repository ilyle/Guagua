package com.xiaoqi.guagua.mvp.view.article.collection

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
import com.xiaoqi.guagua.R
import com.xiaoqi.guagua.mvp.model.bean.Essay
import com.xiaoqi.guagua.mvp.presenter.CollectionPresenter
import com.xiaoqi.guagua.mvp.view.article.essay.EssayAdapter

class CollectionFragment: Fragment(), CollectionView{

    private lateinit var mPresenter: CollectionPresenter

    private lateinit var mRvCollection: RecyclerView
    private lateinit var mTvCollectionNothing: AppCompatTextView

    private lateinit var mAdapter: EssayAdapter

    companion object {
        fun newInstance(): CollectionFragment {
            return CollectionFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_collection, container, false)
        initView(view)
        return view
    }

    override fun onResume() {
        super.onResume()
        mPresenter.getCollection(-1)
    }

    override fun isActive(): Boolean {
        return isAdded && isResumed
    }

    override fun showCollection(essayList: MutableList<Essay>) {
        mAdapter.update(essayList)
        mRvCollection.adapter = mAdapter
    }

    override fun showEmptyView(toShow: Boolean) {
        mTvCollectionNothing.visibility = if (toShow) View.VISIBLE else View.INVISIBLE
        mRvCollection.visibility = if (toShow) View.INVISIBLE else View.VISIBLE
    }

    override fun initView(view: View) {
        mRvCollection = view.findViewById(R.id.rv_collection)
        mRvCollection.layoutManager = LinearLayoutManager(context)
        mAdapter = EssayAdapter(context, mutableListOf())
        mRvCollection.adapter = mAdapter
        mTvCollectionNothing = view.findViewById(R.id.tv_collection_nothing)
    }

    override fun setPresenter(presenter: CollectionPresenter) {
        mPresenter = presenter
    }
}