package com.xiaoqi.guagua.util

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions

/**
 * Created by xujie on 2018/12/11.
 * Mail : 617314917@qq.com
 */
object GlideUtil {
    val requestOptions = RequestOptions()
            .placeholder(ColorDrawable(Color.WHITE))
            .error(ColorDrawable(Color.WHITE))
            .fallback(ColorDrawable(Color.WHITE))

}