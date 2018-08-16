package com.xiaoqi.guagua.mvp.view.detail

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.Toolbar
import android.text.Html
import android.view.*
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
        setHasOptionsMenu(true)
        return view
    }

    private fun initView(view: View) {
        mTbDetail = view.findViewById(R.id.tb_detail)
        mTbDetail.setNavigationOnClickListener { activity?.onBackPressed() }
        mTbDetail.inflateMenu(R.menu.toolbar_detail_menu)
        mTbDetail.setOnMenuItemClickListener(mTbMenuListener)
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


    private val mTbMenuListener = Toolbar.OnMenuItemClickListener {
        when(it.itemId) {
            R.id.tb_detail_menu -> { onDetailMenuClick() }
        }
        true
    }

    private fun onDetailMenuClick() {

    }

    private fun copyLink(url: String) {
        val manager = context?.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clipData = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            ClipData.newPlainText("link", Html.fromHtml(url, Html.FROM_HTML_MODE_LEGACY))
        } else {
            ClipData.newPlainText("link", Html.fromHtml(url))
        }
        manager.primaryClip = clipData
    }
}