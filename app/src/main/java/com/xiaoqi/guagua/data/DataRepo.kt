package com.xiaoqi.guagua.data

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.xiaoqi.guagua.bean.Essay

data class EssayData(
        @Expose
        @SerializedName("errorCode")
        var errorCode: Int,

        @Expose
        @SerializedName("errorMsg")
        var errorMsg: String,

        @Expose
        @SerializedName("data")
        var data: Data
)

data class Data(
        @Expose
        @SerializedName("curPage")
        var curPage: Int,

        @Expose
        @SerializedName("data")
        var data: List<Essay>,

        @Expose
        @SerializedName("offset")
        var offset: Int,

        @Expose
        @SerializedName("over")
        var over: Boolean,

        @Expose
        @SerializedName("pageCount")
        var pageCount: Int,

        @Expose
        @SerializedName("size")
        var size: Int,

        @Expose
        @SerializedName("total")
        var total: Int
)