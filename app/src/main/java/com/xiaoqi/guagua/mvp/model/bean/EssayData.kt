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

        class Essay {
            @SerializedName("apkLink")
            var apkLink: String? = null
            @SerializedName("author")
            var author: String? = null
            @SerializedName("chapterId")
            var chapterId: Int = 0
            @SerializedName("chapterName")
            var chapterName: String? = null
            @SerializedName("collect")
            var isCollect: Boolean = false
            @SerializedName("courseId")
            var courseId: Int = 0
            @SerializedName("desc")
            var desc: String? = null
            @SerializedName("envelopePic")
            var envelopePic: String? = null
            @SerializedName("fresh")
            var isFresh: Boolean = false
            @SerializedName("id")
            var id: Int = 0
            @SerializedName("link")
            var link: String? = null
            @SerializedName("niceDate")
            var niceDate: String? = null
            @SerializedName("origin")
            var origin: String? = null
            @SerializedName("projectLink")
            var projectLink: String? = null
            @SerializedName("publishTime")
            var publishTime: Long = 0
            @SerializedName("superChapterId")
            var superChapterId: Int = 0
            @SerializedName("superChapterName")
            var superChapterName: String? = null
            @SerializedName("title")
            var title: String? = null
            @SerializedName("type")
            var type: Int = 0
            @SerializedName("userId")
            var userId: Int = 0
            @SerializedName("visible")
            var visible: Int = 0
            @SerializedName("zan")
            var zan: Int = 0
            @SerializedName("tags")
            var tags: List<Tags>? = null

            class Tags {
                @SerializedName("name")
                var name: String? = null
                @SerializedName("url")
                var url: String? = null
            }
        }
    }
}
