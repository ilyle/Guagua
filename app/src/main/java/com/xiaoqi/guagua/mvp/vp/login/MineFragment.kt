package com.xiaoqi.guagua.mvp.vp.login

import android.view.View
import android.widget.Button
import com.xiaoqi.guagua.BaseFragment
import com.xiaoqi.guagua.MainActivity
import com.xiaoqi.guagua.R
import com.xiaoqi.guagua.mvp.model.bean.User
import com.xiaoqi.guagua.mvp.model.bean.UserInfo
import com.xiaoqi.guagua.util.MmkvUtil
import com.xiaoqi.guagua.util.ToastUtil

class MineFragment : BaseFragment(), MineView, View.OnClickListener {

    private lateinit var mPresenter: UserPresenter
    private lateinit var mBtnLogout: Button
    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_mine_logout -> {
                UserInfo.user.let { mPresenter.logout(it.id) }
            }
        }
    }

    override fun isActive(): Boolean {
        return isAdded && isResumed
    }

    override fun logoutSuccess(user: User) {
        ToastUtil.showMsg(getString(R.string.logout_success))
        setUser(user)
    }

    private fun setUser(user: User) {
        MmkvUtil.setUserId(-1)
        MmkvUtil.setUserName("")
        MmkvUtil.setUserPw("")
        MmkvUtil.setUserIsLogin(false)
        navigate2Main()
    }

    private fun navigate2Main() {
        activity?.onBackPressed()
    }

    override fun showLogoutFail(errorMsg: String) {
        ToastUtil.showMsg(errorMsg)
    }

    override fun showNetworkError(errorMsg: String) {
        ToastUtil.showMsg(errorMsg)
    }

    override fun setPresenter(presenter: UserPresenter) {
        mPresenter = presenter
    }

    override fun getResource(): Int {
        return R.layout.fragment_mine
    }

    override fun initView(view: View) {
        mBtnLogout = view.findViewById(R.id.btn_mine_logout)
        mBtnLogout.setOnClickListener(this)
    }

    companion object {
        fun newInstance(): MineFragment {
            return MineFragment()
        }
    }
}