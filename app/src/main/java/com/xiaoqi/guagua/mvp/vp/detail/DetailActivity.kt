package com.xiaoqi.guagua.mvp.vp.detail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.xiaoqi.guagua.R
import com.xiaoqi.guagua.mvp.model.bean.Article
import com.xiaoqi.guagua.mvp.model.source.impl.CollectionDataSourceImpl
import com.xiaoqi.guagua.mvp.model.source.local.CollectionDataSourceLocal

class DetailActivity : AppCompatActivity() {

    private lateinit var mDetailFragment: DetailFragment

    companion object {
        fun startAction(context: Context, article: Article) {
            val intent = Intent(context, DetailActivity::class.java)
            intent.putExtra(DetailActivity.ARTICLE, article) // Article实现Parcelable接口，可以在Activity间传输
            intent.putExtra(DetailActivity.IS_FROM_ARTICLE, true)
            context.startActivity(intent)
        }

        fun startAction(context: Context, url: String, isFromArticle: Boolean) {
            val intent = Intent(context, DetailActivity::class.java)
            intent.putExtra(DetailActivity.URL, url)
            intent.putExtra(DetailActivity.IS_FROM_ARTICLE, isFromArticle)
            context.startActivity(intent)
        }

        const val ARTICLE: String = "ARTICLE"
        const val URL: String = "URL"
        const val IS_FROM_ARTICLE = "IS_FROM_ARTICLE"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_common)
        mDetailFragment = if (savedInstanceState != null) {
            supportFragmentManager.getFragment(savedInstanceState, DetailFragment::class.java.simpleName) as DetailFragment
        } else {
            DetailFragment.newInstance()
        }
        supportFragmentManager.beginTransaction().replace(R.id.fl_activity_common, mDetailFragment).commit() // 展示DetailFragment

        /*
        构建DetailPresenter，用有view和model实例
         */
        DetailPresenterImpl.build(mDetailFragment, CollectionDataSourceImpl.getInstance(CollectionDataSourceLocal.getInstance()))
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        if (mDetailFragment.isAdded) {
            supportFragmentManager.putFragment(outState, DetailFragment::class.java.simpleName, mDetailFragment)
        }
    }
}
