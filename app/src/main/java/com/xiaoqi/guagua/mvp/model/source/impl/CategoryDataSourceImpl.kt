package com.xiaoqi.guagua.mvp.model.source.impl

import com.xiaoqi.guagua.mvp.model.bean.Category
import com.xiaoqi.guagua.mvp.model.source.CategoryDataSource
import com.xiaoqi.guagua.mvp.model.source.remote.CategoryDataSourceRemote
import io.reactivex.Observable

class CategoryDataSourceImpl(remote: CategoryDataSourceRemote) : CategoryDataSource {

    private val mRemote = remote

    companion object {
        private var mInstance: CategoryDataSourceImpl? = null

        fun getInstance(remote: CategoryDataSourceRemote): CategoryDataSourceImpl {
            if (mInstance != null) {
                mInstance = CategoryDataSourceImpl(remote)
            }
            return mInstance!!
        }
    }

    override fun listCategory(): Observable<MutableList<Category>> {
        return mRemote.listCategory()
    }
}