package com.xiaoqi.guagua.mvp.view

import android.graphics.Color
import android.support.v7.widget.SearchView
import android.support.v7.widget.Toolbar
import android.view.View
import android.widget.FrameLayout
import android.widget.TextView
import com.xiaoqi.guagua.BaseFragment
import com.xiaoqi.guagua.R
import com.xiaoqi.guagua.mvp.presenter.SearchPresenter
import com.xiaoqi.guagua.util.ToastUtil

/**
 * Created by xujie on 2018/8/20.
 */
class SearchFragment : BaseFragment(), com.xiaoqi.guagua.mvp.view.search.SearchView {


    private lateinit var mTbSearch: Toolbar
    private lateinit var mSvSearch: SearchView
    private lateinit var mSacSearch: SearchView.SearchAutoComplete
    private lateinit var mFlSearch: FrameLayout

    private lateinit var mPresenter: SearchPresenter

    companion object {
        fun newInstance(): SearchFragment {
            return SearchFragment()
        }
    }

    override fun getResource(): Int {
        return R.layout.fragment_search
    }

    override fun initView(view: View) {
        mTbSearch = view.findViewById(R.id.tb_search)
        mSvSearch = view.findViewById(R.id.sv_search)
        mSvSearch.onActionViewExpanded()
        mSvSearch.isSubmitButtonEnabled = true
        mSvSearch.setOnQueryTextListener(mQueryTextListener)
        mSacSearch = mSvSearch.findViewById<SearchView.SearchAutoComplete>(R.id.search_src_text)
        mSacSearch.setTextColor(resources.getColor(R.color.white)) // 设置输入文字的颜色
        mSacSearch.setHintTextColor(resources.getColor(R.color.white)) // 设置提示文字的颜色
    }

    private val mQueryTextListener = object : SearchView.OnQueryTextListener {
        override fun onQueryTextSubmit(query: String): Boolean {
            return false
        }

        override fun onQueryTextChange(newText: String): Boolean {
            return false
        }
    }

    override fun setPresenter(presenter: SearchPresenter) {
        mPresenter = presenter
    }
}