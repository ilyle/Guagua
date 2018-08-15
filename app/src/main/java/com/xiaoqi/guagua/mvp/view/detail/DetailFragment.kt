package com.xiaoqi.guagua.mvp.view.detail

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.Toolbar
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import com.just.agentweb.AgentWeb
import com.xiaoqi.guagua.R

class DetailFragment : Fragment() {

    private lateinit var mUrl: String
    private lateinit var mTitle: String

    private lateinit var mTbDetail: Toolbar
    private lateinit var mFlDetail: FrameLayout

    private lateinit var mAgentWeb: AgentWeb
    companion object {
        fun newInstance() : DetailFragment {
            return DetailFragment()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val intent = activity?.intent!!
        mUrl = intent.getStringExtra(DetailActivity.LINK)
        mTitle = intent.getStringExtra(DetailActivity.TITLE)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_detail, container, false)
        initView(view)
        load(mUrl)
        return view
    }

    private fun initView(view: View) {
        mTbDetail = view.findViewById(R.id.tb_detail)
        mTbDetail.setNavigationOnClickListener { activity?.onBackPressed() }
        mFlDetail = view.findViewById(R.id.fl_detail)
    }


    private fun load(url: String) {
        mAgentWeb = AgentWeb.with(this)
                .setAgentWebParent(mFlDetail, FrameLayout.LayoutParams(-1, -1))
                .useDefaultIndicator()
                .createAgentWeb()
                .ready()
                .go(url)
    }
}