package com.xiaoqi.guagua.mvp.vp.search

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.xiaoqi.guagua.R

/**
 * Created by xujie on 2018/9/28.
 * 搜索历史RecyclerView适配器
 */
class SearchHistoryAdapter(private val mContext: Context, private var mSearchHistoryList: List<String>, private val mListener: SearchHistoryClickListener) : RecyclerView.Adapter<SearchHistoryAdapter.SearchHistoryHolder>() {

    override fun getItemCount(): Int {
        return mSearchHistoryList.size
    }

    override fun onBindViewHolder(p0: SearchHistoryHolder, p1: Int) {
        p0.setData(p1)
    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): SearchHistoryHolder {
        return SearchHistoryHolder(LayoutInflater.from(mContext).inflate(R.layout.item_search_history, p0, false))
    }

    fun updateData(searchHistoryList: List<String>) {
        mSearchHistoryList = searchHistoryList
        notifyDataSetChanged()
    }

    inner class SearchHistoryHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val mTvSearchHistory = itemView.findViewById<TextView>(R.id.tv_search_history)

        fun setData(position: Int) {
            val searchHistory = mSearchHistoryList[position]
            mTvSearchHistory.text = searchHistory
            mTvSearchHistory.setOnClickListener { mListener.onSearchHistoryClickListener(searchHistory) }
        }
    }

    interface SearchHistoryClickListener{
        fun onSearchHistoryClickListener(search: String)
    }
}