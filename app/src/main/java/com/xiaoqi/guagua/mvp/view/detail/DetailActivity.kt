package com.xiaoqi.guagua.mvp.view.detail

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.xiaoqi.guagua.R
import kotlinx.android.synthetic.main.activity_detail.view.*

class DetailActivity : AppCompatActivity() {

    private lateinit var detailFragment: DetailFragment

    companion object {
        const val LINK: String = "Link"
        const val TITLE: String = "Title"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        detailFragment = if (savedInstanceState != null) {
            supportFragmentManager.getFragment(savedInstanceState, DetailFragment::class.java.simpleName) as DetailFragment
        } else {
            DetailFragment.newInstance()
        }
        supportFragmentManager.beginTransaction().replace(R.id.fl_activity_detail, detailFragment).commit() // 展示DetailFragment
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        if (detailFragment.isAdded) {
            supportFragmentManager.putFragment(outState, DetailFragment::class.java.simpleName, detailFragment)
        }
    }
}
