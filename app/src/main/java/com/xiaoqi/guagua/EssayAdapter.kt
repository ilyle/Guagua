package com.xiaoqi.guagua

import android.content.Context
import android.support.v7.widget.AppCompatButton
import android.support.v7.widget.AppCompatTextView
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.xiaoqi.guagua.mvp.model.bean.EssayData

class EssayAdapter(context: Context?, essayList: MutableList<EssayData.Data.Essay>): RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var mContext = context
    private var mEssayList = essayList

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): RecyclerView.ViewHolder {
        return EssayHolder(LayoutInflater.from(mContext).inflate(R.layout.item_essay, p0, false))
    }

    override fun getItemCount(): Int {
        return mEssayList.size
    }

    override fun onBindViewHolder(p0: RecyclerView.ViewHolder, p1: Int) {
        val essayHolder = p0 as EssayHolder
        val essay = mEssayList[p1]
        essayHolder.mBtnEssayChapterName.text = essay.chapterName
        essayHolder.mTvEssayDate.text = essay.niceDate
        essayHolder.mTvEssayTitle.text = essay.title
        essayHolder.mTvEssayAuthor.text = essay.author
    }

    fun update(essayList: List<EssayData.Data.Essay>) {
        mEssayList.clear()
        mEssayList.addAll(essayList)
        notifyDataSetChanged()
        notifyItemChanged(essayList.size)
    }

    inner class EssayHolder(view: View): RecyclerView.ViewHolder(view) {
        var mBtnEssayChapterName: AppCompatButton = view.findViewById(R.id.btn_item_essay_chapter_name)
        var mTvEssayDate: AppCompatTextView = view.findViewById(R.id.tv_item_essay_date)
        var mTvEssayTitle: TextView = view.findViewById(R.id.tv_item_essay_title)
        var mTvEssayAuthor: AppCompatTextView = view.findViewById(R.id.tv_item_essay_author)
    }
}