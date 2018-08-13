package com.xiaoqi.guagua.mvp.model.source.impl

import com.xiaoqi.guagua.mvp.model.bean.EssayData
import com.xiaoqi.guagua.mvp.model.source.EssayDataSource
import com.xiaoqi.guagua.mvp.model.source.remote.EssayDataSourceRemote
import io.reactivex.Observable

class EssayDataSourceImpl(essayDataSourceRemote: EssayDataSourceRemote): EssayDataSource {

    private var mEssayDataSourceRemote = essayDataSourceRemote

    companion object {

        private var instance: EssayDataSourceImpl? = null

        fun getInstance(essayDataSourceRemote: EssayDataSourceRemote): EssayDataSourceImpl {
            if (instance == null) {
                instance = EssayDataSourceImpl(essayDataSourceRemote)
            }
            return instance!!
        }
    }

    private var mEssayCache: LinkedHashMap<Int, EssayData.Data.Essay>? = null
    private val index: Int = 0

    override fun getEssay(page: Int, forceUpdate: Boolean, clearCache: Boolean): Observable<List<EssayData.Data.Essay>> {
        /*
        !forceUpdate，不更新，即用户按HOME键再返回APP，此时返回缓存的文章列表
         */
        if (!forceUpdate && mEssayCache != null) {

        }
        /*
        forceUpdate&&!clearCache，更新且不清缓存，即用户向下滑动列表的情况，此时请求数据并保存缓存
         */
        if (!clearCache && mEssayCache != null) {

        }
        /*
        forceUpdate && cleanCache，更新且清缓存，即下拉刷新，还有第一次加载的情况
         */
        return mEssayDataSourceRemote.getEssay(index, forceUpdate, clearCache)
                .doOnNext { refreshEssayCache(clearCache, it) }
    }

    /**
     * 刷新文章缓存
     */
    private fun refreshEssayCache(clearCache: Boolean, essayList: List<EssayData.Data.Essay>) {
        if (mEssayCache == null) {
            mEssayCache = LinkedHashMap()
        }
        if (clearCache) {
            mEssayCache?.clear()
        }
        for (essay in essayList) {
            mEssayCache?.put(essay.id, essay)
        }
    }

}