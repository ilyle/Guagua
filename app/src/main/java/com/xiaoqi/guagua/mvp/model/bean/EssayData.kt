package com.xiaoqi.guagua.mvp.model.bean

import com.google.gson.annotations.SerializedName

class EssayData {
    @SerializedName("data")
    var data: Data? = null
    @SerializedName("errorCode")
    var errorCode: Int = 0
    @SerializedName("errorMsg")
    var errorMsg: String? = null

    class Data {
        @SerializedName("curPage")
        var curPage: Int = 0
        @SerializedName("offset")
        var offset: Int = 0
        @SerializedName("over")
        var isOver: Boolean = false
        @SerializedName("pageCount")
        var pageCount: Int = 0
        @SerializedName("size")
        var size: Int = 0
        @SerializedName("total")
        var total: Int = 0
        @SerializedName("datas")
        var datas: List<Essay>? = null
    }
}
