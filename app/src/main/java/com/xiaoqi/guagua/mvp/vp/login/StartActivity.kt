package com.xiaoqi.guagua.mvp.vp.login

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.TextView
import com.dinuscxj.progressbar.CircleProgressBar
import com.xiaoqi.guagua.MainActivity
import com.xiaoqi.guagua.R
import com.xiaoqi.guagua.mvp.model.bean.User
import com.xiaoqi.guagua.mvp.model.bean.UserInfo
import com.xiaoqi.guagua.mvp.model.source.impl.UserDataSourceImpl
import com.xiaoqi.guagua.mvp.model.source.remote.UserDataSourceRemote
import com.xiaoqi.guagua.util.PreferenceUtil
import com.xiaoqi.guagua.util.ToastUtil
import com.xiaoqi.guagua.util.coroutines.taskHeartbeat
import com.xiaoqi.guagua.util.coroutines.taskLaunch
import com.xiaoqi.guagua.util.coroutines.taskRunOnUiThread
import kotlinx.coroutines.experimental.Job

/**
 * Created by Xujie on 2018/11/2
 * mail: jiexu215936@sohu-inc.com
 */
class StartActivity : AppCompatActivity(), LoginView {

    private lateinit var mPbSkip: CircleProgressBar
    private lateinit var mTvSkip: TextView
    private var mTask: Job? = null
    private var mCount: Int = 3

    private lateinit var mPresenter: UserPresenter

    override fun isActive(): Boolean {
        return true
    }

    override fun loginSuccess(user: User) {
        PreferenceUtil.setUser(user) // 保存User对象，此时服务器会刷新token过期时间
        MainActivity.startAction(this)
        finish()
    }

    override fun showLoginFail(errorMsg: String) {
        ToastUtil.showMsg(errorMsg)
    }

    override fun showNetworkError(errorMsg: String) {
        ToastUtil.showMsg(errorMsg)
    }

    override fun setPresenter(presenter: UserPresenter) {
        mPresenter = presenter
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)
        init()
        UserPresenterImpl.build(this, UserDataSourceImpl.getInstance(UserDataSourceRemote.getInstance()))
        setupTask()
    }

    private fun init() {
        mPbSkip = findViewById(R.id.pb_start_skip)
        mTvSkip = findViewById(R.id.tv_start_skip)
    }

    override fun onDestroy() {
        super.onDestroy()
        mTask?.cancel()
        mTask = null
    }

    private fun setupTask() {
        mTask?.cancel()
        mTask = taskLaunch {
            taskHeartbeat(mCount, 1_000) {
                taskRunOnUiThread {
                    mTvSkip.text = mCount.toString()
                    mPbSkip.progress = (100 * (3 - mCount) / 3.0f).toInt()
                    mCount--
                    if (mCount == 0) {
                        nav2Main()
                    }
                }
            }
        }
    }

    /**
     * 跳转到MainActivity
     */
    private fun nav2Main() {
        val user = UserInfo.user
        user?.token?.let {
            mPresenter.login(it) // 尝试自动登录
        }
        if (user?.token == null) {
            MainActivity.startAction(this)
            finish()
        }
    }
}