package com.xiaoqi.guagua.mvp.vp.detail

import android.content.*
import android.net.Uri
import android.os.Build
import android.os.Bundle
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
import com.xiaoqi.guagua.BaseFragment
import com.xiaoqi.guagua.R
import com.xiaoqi.guagua.mvp.model.bean.Article
import com.xiaoqi.guagua.util.AppUtil
import com.xiaoqi.guagua.util.ToastUtil

class DetailFragment : BaseFragment(), View.OnClickListener, DetailView {

    private lateinit var mArticle: Article

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
        mArticle = intent.getParcelableExtra(DetailActivity.ARTICLE)
        mArticle.timestamp = System.currentTimeMillis()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_detail, container, false)
        initView(view)

        return view
    }

    override fun getResource(): Int {
        return R.layout.fragment_detail
    }

    override fun initView(view: View) {
        mTbDetail = view.findViewById(R.id.tb_detail)
        mTbDetail.setNavigationOnClickListener { activity?.onBackPressed() }
        mTbDetail.inflateMenu(R.menu.toolbar_detail_menu)
        mTbDetail.setOnMenuItemClickListener(mTbDetailListener)
        mFlDetail = view.findViewById(R.id.fl_detail)
    }

    override fun onResume() {
        super.onResume()
        load(mArticle.link!!)
        mPresenter.checkIsCollection(USER_ID_DEFAULT, mArticle)
    }

    override fun onPause() {
        super.onPause()
        mAgentWeb.webLifeCycle.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        mAgentWeb.webLifeCycle.onDestroy()
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
                copyLink(mArticle.link!!)
                ToastUtil.showMsg(resources.getString(R.string.toast_copy_link))
            }
            R.id.tv_detail_browser -> {
                AppUtil.openInBrowser(context, mArticle.link!!)
            }
        }
    }

    private fun load(url: String) {
        mAgentWeb = AgentWeb.with(this)
                .setAgentWebParent(mFlDetail, FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT))
                .useDefaultIndicator()
                .createAgentWeb()
                .ready()
                .go(url)
    }


    private val mTbDetailListener = Toolbar.OnMenuItemClickListener {
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
            resources.getString(R.string.detail_not_collect)
        } else {
            resources.getString(R.string.detail_collect)
        }
        mMenuDetail.show()
    }


    private fun collect() {
        if (isCollected) {
            mPresenter.removeCollection(USER_ID_DEFAULT, mArticle)
        } else {
            mPresenter.insertCollection(USER_ID_DEFAULT, mArticle)
        }
        mPresenter.checkIsCollection(USER_ID_DEFAULT, mArticle)
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