package com.xiaoqi.guagua.mvp.vp.category

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import com.xiaoqi.guagua.R
import com.xiaoqi.guagua.mvp.model.bean.Category
import com.xiaoqi.guagua.mvp.vp.search.SearchActivity
import com.xiaoqi.guagua.util.ToastUtil
import com.zhy.view.flowlayout.FlowLayout
import com.zhy.view.flowlayout.TagAdapter
import com.zhy.view.flowlayout.TagFlowLayout

class CategoryRecyclerViewAdapter(context: Context?, categoryList: MutableList<Category>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val mContext = context
    private val mCategoryList = categoryList

    override fun onBindViewHolder(p0: RecyclerView.ViewHolder, p1: Int) {
        val categoryHolder = p0 as CategoryHolder
        categoryHolder.setData(p1)
    }

    override fun getItemCount(): Int {
        return mCategoryList.size
    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): RecyclerView.ViewHolder {
        return CategoryHolder(LayoutInflater.from(mContext).inflate(R.layout.item_category, p0, false))
    }

    fun update(categoryList: List<Category>) {
        mCategoryList.clear()
        mCategoryList.addAll(categoryList)
        notifyDataSetChanged()
        notifyItemChanged(categoryList.size)
    }

    inner class CategoryHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val mTvCategory = view.findViewById<TextView>(R.id.tv_item_category) // 展示一级分类
        private val mTflCategory = view.findViewById<TagFlowLayout>(R.id.tfl_category) // 展示二级分类

        fun setData(position: Int) {
            val category = mCategoryList[position] // 一级分类
            val categoryList: MutableList<Category>? = category.children // 二级分类
            mTvCategory.text = category.name
            mTflCategory.adapter = object : TagAdapter<Category>(categoryList) {
                override fun getView(parent: FlowLayout?, position: Int, t: Category?): View? {
                    val view = LayoutInflater.from(parent?.context).inflate(R.layout.item_tfl_category, mTflCategory, false) as TextView
                    if (t == null) {
                        return null
                    } else {
                        view.text = t.name
                    }
                    mTflCategory.setOnTagClickListener { _, position, _ -> onTagClickListener(position, categoryList) }
                    return view
                }
            }
        }

        private fun onTagClickListener(position: Int, categoryList: MutableList<Category>?): Boolean {
            SearchActivity.startActivity(mContext!!, categoryList!![position].name.toString())
            return true
        }
    }
}