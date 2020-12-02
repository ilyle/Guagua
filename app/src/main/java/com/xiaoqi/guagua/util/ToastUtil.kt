package com.xiaoqi.guagua.util

import com.xiaoqi.base.toast.BaseToast
import com.xiaoqi.guagua.MainApplication

object ToastUtil {
    fun showMsg(msg : String) {
        BaseToast.showMsg(MainApplication.getContext(), msg)
    }
}