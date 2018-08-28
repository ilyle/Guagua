package com.xiaoqi.guagua.mvp.model.source.remote

import com.xiaoqi.guagua.mvp.model.bean.Category
import com.xiaoqi.guagua.mvp.model.source.CategoryDataSource
import com.xiaoqi.guagua.retrofit.RetrofitClient
import com.xiaoqi.guagua.retrofit.RetrofitService
import com.xiaoqi.guagua.util.SortDescendUtil

import io.reactivex.Observable
import retrofit2.Retrofit

class CategoryDataSourceRemote : CategoryDataSource {

    companion object {
        private var mInstance: CategoryDataSourceRemote? = null

        fun getInstance(): CategoryDataSourceRemote {
            if (mInstance == null) {
                mInstance = CategoryDataSourceRemote()
            }
            return mInstance!!
        }
    }

    override fun listCategory(): Observable<MutableList<Category>> {
        return RetrofitClient.getInstance()
                .create(RetrofitService::class.java)
                .listCategory()
                .filter { it.errorCode != -1 }
                .flatMap { Observable.fromIterable(it.data).toSortedList { c1, c2 -> SortDescendUtil.sortCategory(c1, c2) }.toObservable() }
    }
}
