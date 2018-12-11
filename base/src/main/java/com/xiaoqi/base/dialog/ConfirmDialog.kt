package com.xiaoqi.base.dialog

import android.app.Dialog
import android.content.Context
import android.support.annotation.ColorInt
import android.support.annotation.StringRes
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import com.xiaoqi.base.R
import com.xiaoqi.base.view.CommonShapeButton
import kotlinx.android.synthetic.main.dialog_confirm.*

/**
 * Created by zxx on 2018/4/8.
 * 确认框
 */

class ConfirmDialog(mContext: Context) : Dialog(mContext) {

    init {

        requestWindowFeature(Window.FEATURE_NO_TITLE)

        val mDecorView: View? = window!!.decorView ?: throw IllegalArgumentException("must init content VIEW")

        val mBootView: View = LayoutInflater.from(mContext)
                .inflate(R.layout.dialog_confirm, mDecorView as ViewGroup?, false)

        setContentView(mBootView)
        mDecorView?.setBackgroundColor(0x00ffffff)
    }


    fun setContent(@StringRes resId: Int): ConfirmDialog {
        tv_dialog_hints.setText(resId)
        return this
    }

    fun setContent(msg: String): ConfirmDialog {
        tv_dialog_hints.text = msg
        return this
    }

    fun setLeftBtnColor(@ColorInt color: Int) {
        btn_dialog_left.mFillColor = color
    }

    fun setRightBtnColor(@ColorInt color: Int) {
        btn_dialog_right.mFillColor = color
    }

    fun setSingleBtnColor(@ColorInt color: Int) {
        dialog_one_btn.mFillColor = color
    }

    fun setLeftBtn(@StringRes resId: Int, l: View.OnClickListener?): ConfirmDialog {
        val listener = l ?: View.OnClickListener { dismiss() }
        btn_dialog_left.setText(resId)
        btn_dialog_left.setOnClickListener(listener)
        return this
    }

    fun setRightBtn(@StringRes resId: Int, l: View.OnClickListener?): ConfirmDialog {
        val listener = l ?: View.OnClickListener { dismiss() }
        btn_dialog_right.setText(resId)
        btn_dialog_right.setOnClickListener(listener)
        return this
    }

    fun setSingleBtn(message: String?, l: View.OnClickListener?): ConfirmDialog {
        val listener = l ?: View.OnClickListener { dismiss() }
        dialog_one_btn.visibility = View.VISIBLE
        dialog_two_btn.visibility = View.GONE
        dialog_one_btn.text = message
        dialog_one_btn.setOnClickListener(listener)
        return this
    }
}
