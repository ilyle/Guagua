package com.xiaoqi.guagua.mvp.model.bean

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import org.litepal.crud.LitePalSupport

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

        /**
         * 单个文章
         */
        class Essay() : Parcelable, LitePalSupport() {
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
            var essayId: Int = 0
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
            var timestamp: Long = 0 // 收藏时间戳
            @SerializedName("tags")
            var tags: List<Tags>? = null

            constructor(parcel: Parcel) : this() {
                apkLink = parcel.readString()
                author = parcel.readString()
                chapterId = parcel.readInt()
                chapterName = parcel.readString()
                isCollect = parcel.readByte() != 0.toByte()
                courseId = parcel.readInt()
                desc = parcel.readString()
                envelopePic = parcel.readString()
                isFresh = parcel.readByte() != 0.toByte()
                essayId = parcel.readInt()
                link = parcel.readString()
                niceDate = parcel.readString()
                origin = parcel.readString()
                projectLink = parcel.readString()
                publishTime = parcel.readLong()
                superChapterId = parcel.readInt()
                superChapterName = parcel.readString()
                title = parcel.readString()
                type = parcel.readInt()
                userId = parcel.readInt()
                visible = parcel.readInt()
                zan = parcel.readInt()
            }

            class Tags {
                @SerializedName("name")
                var name: String? = null
                @SerializedName("url")
                var url: String? = null
            }

            override fun writeToParcel(parcel: Parcel, flags: Int) {
                parcel.writeString(apkLink)
                parcel.writeString(author)
                parcel.writeInt(chapterId)
                parcel.writeString(chapterName)
                parcel.writeByte(if (isCollect) 1 else 0)
                parcel.writeInt(courseId)
                parcel.writeString(desc)
                parcel.writeString(envelopePic)
                parcel.writeByte(if (isFresh) 1 else 0)
                parcel.writeInt(essayId)
                parcel.writeString(link)
                parcel.writeString(niceDate)
                parcel.writeString(origin)
                parcel.writeString(projectLink)
                parcel.writeLong(publishTime)
                parcel.writeInt(superChapterId)
                parcel.writeString(superChapterName)
                parcel.writeString(title)
                parcel.writeInt(type)
                parcel.writeInt(userId)
                parcel.writeInt(visible)
                parcel.writeInt(zan)
            }

            override fun describeContents(): Int {
                return 0
            }

            companion object CREATOR : Parcelable.Creator<Essay> {
                override fun createFromParcel(parcel: Parcel): Essay {
                    return Essay(parcel)
                }

                override fun newArray(size: Int): Array<Essay?> {
                    return arrayOfNulls(size)
                }
            }
        }
    }
}
