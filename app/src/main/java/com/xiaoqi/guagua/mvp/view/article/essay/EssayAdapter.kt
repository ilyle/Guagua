package com.xiaoqi.guagua.mvp.view.article.essay

import android.content.Context
import android.content.Intent
import android.support.v7.widget.AppCompatButton
import android.support.v7.widget.AppCompatTextView
import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.xiaoqi.guagua.R
import com.xiaoqi.guagua.mvp.model.bean.EssayData
import com.xiaoqi.guagua.mvp.view.detail.DetailActivity

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
        essayHolder.setData(p1)
    }

    fun update(essayList: List<EssayData.Data.Essay>) {
        mEssayList.clear()
        mEssayList.addAll(essayList)
        notifyDataSetChanged()
        notifyItemChanged(essayList.size)
    }

    inner class EssayHolder(view: View): RecyclerView.ViewHolder(view), View.OnClickListener{

        private var mBtnEssayChapterName: AppCompatButton = view.findViewById(R.id.btn_item_essay_chapter_name)
        private var mTvEssayDate: AppCompatTextView = view.findViewById(R.id.tv_item_essay_date)
        private var mTvEssayTitle: TextView = view.findViewById(R.id.tv_item_essay_title)
        private var mTvEssayAuthor: AppCompatTextView = view.findViewById(R.id.tv_item_essay_author)
        private var mCvEssay: CardView = view.findViewById(R.id.cv_item_essay)

        fun setData(position: Int) {
            val essay = mEssayList[position]
            mBtnEssayChapterName.text = essay.chapterName
            mTvEssayDate.text = essay.niceDate
            mTvEssayTitle.text = essay.title
            mTvEssayAuthor.text = essay.author
            mCvEssay.setOnClickListener(this)
        }

        override fun onClick(p0: View?) {
            when (p0?.id) {
            /**
             * 点击CardView跳转WebView
             */
                R.id.cv_item_essay -> {
                    val intent = Intent(mContext, DetailActivity::class.java)
                    val essay = mEssayList[adapterPosition]
                    intent.putExtra(DetailActivity.LINK, essay.link)
                    intent.putExtra(DetailActivity.TITLE, essay.title)
                    mContext?.startActivity(intent)
                }
            }
        }
    }
}