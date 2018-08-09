package com.xiaoqi.guagua.data.source

import android.support.annotation.NonNull
import com.xiaoqi.guagua.bean.Essay
import io.reactivex.Observable

interface EssayDataSource {
    fun getEssay(@NonNull page: Int): Observable<List<Essay>>
}