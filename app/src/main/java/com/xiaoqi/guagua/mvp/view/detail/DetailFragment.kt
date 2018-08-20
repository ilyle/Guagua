package com.xiaoqi.guagua.mvp.view.detail

import android.content.*
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.AppCompatTextView
import android.support.v7.widget.Toolbar
import android.text.Html
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import com.just.agentweb.AgentWeb
import com.xiaoqi.base.dialog.BaseDialog
import com.xiaoqi.guagua.R
import com.xiaoqi.guagua.mvp.model.bean.Essay
import com.xiaoqi.guagua.mvp.presenter.DetailPresenter
import com.xiaoqi.guagua.util.ToastUtil

class DetailFragment : Fragment(), View.OnClickListener, DetailView {

    private lateinit var mEssay: Essay

    private lateinit var mTbDetail: Toolbar
    private lateinit var mFlDetail: FrameLayout
    private lateinit var mMenuDetail: BaseDialog

    private lateinit var mAgentWeb: AgentWeb

    private lateinit var mPresenter: DetailPresenter

    private var isCollected: Boolean = false

    companion object {
        private const val USER_ID_DEFAULT = -1 // 未登陆时的用户id

        fun newInstance(): DetailFragment {
            return DetailFragment()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val intent = activity?.intent!!
        mEssay = intent.getParcelableExtra(DetailActivity.ESSAY)
        mEssay.timestamp = System.currentTimeMillis()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_detail, container, false)
        initView(view)
        load(mEssay.link!!)
        mPresenter.checkIsCollection(USER_ID_DEFAULT, mEssay)
        return view
    }

    override fun onPause() {
        mAgentWeb.webLifeCycle.onPause()
        super.onPause()
    }

    override fun onDestroy() {
        mAgentWeb.webLifeCycle.onDestroy()
        super.onDestroy()
    }

    override fun onClick(p0: View?) {
        if (mMenuDetail.isShowing) {
            mMenuDetail.dismiss()
        }
        when (p0?.id) {
            R.id.tv_detail_collect -> {
                collect()
            }
            R.id.tv_detail_share -> {
            }
            R.id.tv_detail_link -> {
                copyLink(mEssay.link!!)
                ToastUtil.showMsg(resources.getString(R.string.toast_copy_link))
            }
            R.id.tv_detail_browser -> {
                openInBrowser()
            }
        }
    }

    override fun initView(view: View) {
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
        when (it.itemId) {
            R.id.tb_detail_menu -> {
                onDetailMenuClick()
            }
        }
        true
    }

    private fun onDetailMenuClick() {
        mMenuDetail = BaseDialog.Builder(context!!)
                .contentView(R.layout.dialog_detail_menu)
                .widthPx(ViewGroup.LayoutParams.MATCH_PARENT)
                .heightPx(ViewGroup.LayoutParams.WRAP_CONTENT)
                .gravity(Gravity.BOTTOM)
                .addViewOnClick(R.id.tv_detail_collect, this)
                .addViewOnClick(R.id.tv_detail_share, this)
                .addViewOnClick(R.id.tv_detail_link, this)
                .addViewOnClick(R.id.tv_detail_browser, this)
                .build()
        mMenuDetail.getContentView().findViewById<AppCompatTextView>(R.id.tv_detail_collect).text = if (isCollected) {
            resources.getString(R.string.tv_detail_not_collect)
        } else {
            resources.getString(R.string.tv_detail_collect)
        }
        mMenuDetail.show()
    }


    private fun collect() {
        if (isCollected) {
            mPresenter.removeCollection(USER_ID_DEFAULT, mEssay)
        } else {
            mPresenter.insertCollection(USER_ID_DEFAULT, mEssay)
        }
        mPresenter.checkIsCollection(USER_ID_DEFAULT, mEssay)
    }

    private fun share() {

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

    private fun openInBrowser() {
        try {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(mEssay.link!!))
            startActivity(intent)
        } catch (e: ActivityNotFoundException) {
            ToastUtil.showMsg(e.toString())
        }
    }

    override fun addToCollectionSuccess() {
        ToastUtil.showMsg(resources.getString(R.string.toast_add_collection_success))
    }

    override fun addToCollectionFail() {
        ToastUtil.showMsg(resources.getString(R.string.toast_add_collection_fail))
    }

    override fun removeCollectionSuccess() {
        ToastUtil.showMsg(resources.getString(R.string.toast_remove_collection_success))
    }

    override fun removeCollectionFail() {
        ToastUtil.showMsg(resources.getString(R.string.toast_remove_collection_fail))
    }

    override fun saveCollectionStatus(isCollected: Boolean) {
        this.isCollected = isCollected
    }

    override fun setPresenter(presenter: DetailPresenter) {
        mPresenter = presenter
    }
}