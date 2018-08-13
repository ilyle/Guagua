package com.xiaoqi.guagua.mvp.model.source

import android.support.annotation.NonNull
import com.xiaoqi.guagua.mvp.model.bean.EssayData
import io.reactivex.Observable

interface EssayDataSource {
    fun getEssay(@NonNull page: Int, @NonNull forceUpdate: Boolean, @NonNull clearCache: Boolean): Observable<List<EssayData.Data.Essay>>
}