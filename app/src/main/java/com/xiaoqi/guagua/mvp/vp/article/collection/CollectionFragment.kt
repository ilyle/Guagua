package com.xiaoqi.guagua.mvp.vp.article.collection

import android.support.v7.widget.AppCompatTextView
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.xiaoqi.guagua.BaseFragment
import com.xiaoqi.guagua.R
import com.xiaoqi.guagua.mvp.model.bean.Article
import com.xiaoqi.guagua.mvp.vp.article.ArticleRecyclerViewAdapter

class CollectionFragment: BaseFragment(), CollectionView {

    private lateinit var mPresenter: CollectionPresenter

    private lateinit var mRvCollection: RecyclerView
    private lateinit var mTvCollectionEmpty: AppCompatTextView

    private lateinit var mAdapter: ArticleRecyclerViewAdapter

    companion object {
        fun newInstance(): CollectionFragment {
            return CollectionFragment()
        }
    }

    override fun getResource(): Int {
        return R.layout.fragment_collection
    }

    override fun initView(view: View) {
        mRvCollection = view.findViewById(R.id.rv_collection)
        mRvCollection.layoutManager = LinearLayoutManager(context)
        mAdapter = ArticleRecyclerViewAdapter(context, mutableListOf())
        mRvCollection.adapter = mAdapter
        mTvCollectionEmpty = view.findViewById(R.id.tv_collection_empty)
    }

    override fun onResume() {
        super.onResume()
        mPresenter.getCollection(-1)
    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenter.unSubscribe()
    }

    override fun isActive(): Boolean {
        return isAdded && isResumed
    }

    override fun showCollection(articleList: MutableList<Article>) {
        mAdapter.update(articleList)
        mRvCollection.adapter = mAdapter
    }

    override fun showEmpty(isEmpty: Boolean) {
        mTvCollectionEmpty.visibility = if (isEmpty) View.VISIBLE else View.INVISIBLE
        mRvCollection.visibility = if (isEmpty) View.INVISIBLE else View.VISIBLE
    }

    override fun setPresenter(presenter: CollectionPresenter) {
        mPresenter = presenter
    }
}