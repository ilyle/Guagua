package com.xiaoqi.guagua.mvp.vp.category

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.xiaoqi.guagua.R
import com.xiaoqi.guagua.mvp.model.bean.Category
import com.zhy.view.flowlayout.TagFlowLayout

class CategoryRecyclerViewAdapter(context: Context?, categoryList: MutableList<Category>) : RecyclerView.Adapter<RecyclerView.ViewHolder> () {

    private val mContext = context
    private val mCategoryList = categoryList

    override fun onBindViewHolder(p0: RecyclerView.ViewHolder, p1: Int) {
        val categoryHolder = p0 as CategoryHolder
        categoryHolder.setData(p1)
    }

    override fun getItemCount(): Int {
        return mCategoryList.size
    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): RecyclerView.ViewHolder{
        return CategoryHolder(LayoutInflater.from(mContext).inflate(R.layout.item_category, p0, false))
    }

    inner class CategoryHolder(view: View) : RecyclerView.ViewHolder(view), View.OnClickListener {

        private val mTvCategory = view.findViewById<TextView>(R.id.tv_item_category)
        private val mTflCategory = view.findViewById<TagFlowLayout>(R.id.tfl_category)

        fun setData(position: Int) {
            val category = mCategoryList[position]
            mTvCategory.text = category.name

        }

        override fun onClick(p0: View?) {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

    }
}