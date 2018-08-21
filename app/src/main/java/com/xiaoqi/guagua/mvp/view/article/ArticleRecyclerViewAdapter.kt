package com.xiaoqi.guagua.mvp.view.article

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
import com.xiaoqi.guagua.mvp.model.bean.Article
import com.xiaoqi.guagua.mvp.view.detail.DetailActivity

class ArticleRecyclerViewAdapter(context: Context?, articleList: MutableList<Article>): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var mContext = context
    private var mArticleList = articleList

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): RecyclerView.ViewHolder {
        return ArticleHolder(LayoutInflater.from(mContext).inflate(R.layout.item_article, p0, false))
    }

    override fun getItemCount(): Int {
        return mArticleList.size
    }

    override fun onBindViewHolder(p0: RecyclerView.ViewHolder, p1: Int) {
        val articleHolder = p0 as ArticleHolder
        articleHolder.setData(p1)
    }

    fun update(articleList: List<Article>) {
        mArticleList.clear()
        mArticleList.addAll(articleList)
        notifyDataSetChanged()
        notifyItemChanged(articleList.size)
    }

    inner class ArticleHolder(view: View): RecyclerView.ViewHolder(view), View.OnClickListener{

        private var mBtnArticleChapterName: AppCompatButton = view.findViewById(R.id.btn_item_article_chapter_name)
        private var mTvArticleDate: AppCompatTextView = view.findViewById(R.id.tv_item_article_date)
        private var mTvArticleTitle: TextView = view.findViewById(R.id.tv_item_article_title)
        private var mTvArticleAuthor: AppCompatTextView = view.findViewById(R.id.tv_item_article_author)
        private var mCvArticle: CardView = view.findViewById(R.id.cv_item_article)

        fun setData(position: Int) {
            val article = mArticleList[position]
            mBtnArticleChapterName.text = article.chapterName
            mTvArticleDate.text = article.niceDate
            mTvArticleTitle.text = article.title
            mTvArticleAuthor.text = article.author
            mCvArticle.setOnClickListener(this)
        }

        override fun onClick(p0: View?) {
            when (p0?.id) {
            /**
             * 点击CardView跳转WebView
             */
                R.id.cv_item_article -> {
                    val intent = Intent(mContext, DetailActivity::class.java)
                    val article = mArticleList[adapterPosition]
                    intent.putExtra(DetailActivity.ARTICLE, article) // Article实现Parcelable接口，可以在Activity间传输
                    mContext?.startActivity(intent)
                }
            }
        }
    }
}