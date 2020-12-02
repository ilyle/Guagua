package com.xiaoqi.guagua.mvp.model.bean

import com.google.gson.annotations.SerializedName

data class CategoryData(
        var errorCode: Int,
        var errorMsg: String?,
        var data: List<Category>?
)

data class Category(
        var courseId: Int,
        var id: Int,
        var name: String?,
        var order: Int,
        var parentChapterId: Int,
        var visible: Int,
        var children: MutableList<Category>?
)
