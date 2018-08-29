package com.xiaoqi.guagua.mvp.model.bean

import com.google.gson.annotations.SerializedName

class Category {
    @SerializedName("courseId")
    var courseId: Int = 0
    @SerializedName("id")
    var id: Int = 0
    @SerializedName("name")
    var name: String? = null
    @SerializedName("order")
    var order: Int = 0
    @SerializedName("parentChapterId")
    var parentChapterId: Int = 0
    @SerializedName("visible")
    var visible: Int = 0
    @SerializedName("children")
    var children: MutableList<Category>? = null
}
