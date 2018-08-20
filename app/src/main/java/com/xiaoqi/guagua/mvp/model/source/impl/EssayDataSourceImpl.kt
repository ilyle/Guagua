package com.xiaoqi.guagua.mvp.model.source.impl

import com.xiaoqi.guagua.mvp.model.bean.EssayData.Data.Essay
import com.xiaoqi.guagua.mvp.model.source.EssayDataSource
import com.xiaoqi.guagua.mvp.model.source.remote.EssayDataSourceRemote
import com.xiaoqi.guagua.util.SortDescendUtil
import io.reactivex.Observable

class EssayDataSourceImpl(essayDataSourceRemote: EssayDataSourceRemote) : EssayDataSource {

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

    private var mEssayCache: LinkedHashMap<Int, Essay>? = null // 缓存数据

    override fun getEssay(page: Int, forceUpdate: Boolean, clearCache: Boolean): Observable<MutableList<Essay>> {
        /*
        !forceUpdate，不更新，即用户按HOME键再返回APP，此时返回缓存的文章列表
         */
        if (!forceUpdate && mEssayCache != null) {
            return Observable.fromIterable(ArrayList(mEssayCache!!.values))
                    .toSortedList { essay1, essay2 -> SortDescendUtil.sortEssay(essay1, essay2) }
                    .toObservable()
        }
        /*
        forceUpdate&&!clearCache，更新且不清缓存，即用户向下滑动列表的情况，此时请求数据并保存缓存
         */
        if (!clearCache && mEssayCache != null) {
            val ob1 = Observable.fromIterable(ArrayList(mEssayCache!!.values))
                    .toSortedList { essay1, essay2 -> SortDescendUtil.sortEssay(essay1, essay2) }
                    .toObservable()
            /*
            请求新的数据
             */
            val ob2 = mEssayDataSourceRemote.getEssay(page, forceUpdate, clearCache)
                    .doOnNext { refreshEssayCache(clearCache, it) }
            /*
            合并数据
             */
            return Observable.merge(ob1, ob2).collect({ mutableListOf<Essay>() }, { t1, t2 -> t1.addAll(t2) }).toObservable()
        }
        /*
        forceUpdate && cleanCache，更新且清缓存，即下拉刷新，还有第一次加载的情况
         */
        return mEssayDataSourceRemote.getEssay(0, forceUpdate, clearCache)
                .doOnNext { refreshEssayCache(clearCache, it) }
    }

    /**
     * 刷新文章缓存
     */
    private fun refreshEssayCache(clearCache: Boolean, essayList: List<Essay>) {
        if (mEssayCache == null) {
            mEssayCache = LinkedHashMap()
        }
        if (clearCache) {
            mEssayCache?.clear()
        }
        for (essay in essayList) {
            mEssayCache?.put(essay.essayId, essay)
        }
    }

}