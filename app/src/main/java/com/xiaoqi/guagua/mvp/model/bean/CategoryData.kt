package com.xiaoqi.guagua.mvp.model.bean

import com.google.gson.annotations.SerializedName

class CategoryData {

    @SerializedName("errorCode")
    var errorCode: Int = 0
    @SerializedName("errorMsg")
    var errorMsg: String? = null
    @SerializedName("data")
    var data: List<Category>? = null

}
