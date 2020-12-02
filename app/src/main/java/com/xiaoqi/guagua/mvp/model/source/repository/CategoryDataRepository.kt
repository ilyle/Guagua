package com.xiaoqi.guagua.mvp.model.source.repository

import com.xiaoqi.guagua.mvp.model.bean.Category
import com.xiaoqi.guagua.mvp.model.source.CategoryDataSource
import com.xiaoqi.guagua.mvp.model.source.remote.CategoryDataSourceRemote
import io.reactivex.Observable

class CategoryDataRepository(remote: CategoryDataSourceRemote) : CategoryDataSource {

    private val mRemote = remote

    companion object {
        private var mInstance: CategoryDataRepository? = null

        fun getInstance(remote: CategoryDataSourceRemote): CategoryDataRepository {
            if (mInstance == null) {
                mInstance = CategoryDataRepository(remote)
            }
            return mInstance!!
        }
    }

    override fun listCategory(): Observable<MutableList<Category>> {
        return mRemote.listCategory()
    }
}