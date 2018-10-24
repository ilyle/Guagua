package com.xiaoqi.guagua.mvp.model.bean

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import org.litepal.crud.LitePalSupport

data class ArticleData(
        var data: Data?,
        var errorCode: Int,
        var errorMsg: String?
)

data class Data(
        var curPage: Int,
        var offset: Int,
        var over: Boolean,
        var pageCount: Int,
        var size: Int,
        var total: Int,
        var datas: List<Article>?
)

/**
 * 单个文章
 */
data class Article(
        var apkLink: String?,
        var author: String?,
        var chapterId: Int,
        var chapterName: String?,
        var isCollect: Boolean,
        var courseId: Int,
        var desc: String?,
        var envelopePic: String?,
        var fresh: Boolean,
        @SerializedName("id")
        var articleId: Int,
        var link: String?,
        var niceDate: String?,
        var origin: String?,
        var projectLink: String?,
        var publishTime: Long,
        var superChapterId: Int,
        var superChapterName: String?,
        var title: String?,
        var type: Int,
        var userId: Int,
        var visible: Int,
        var zan: Int,
        var timestamp: Long,
        var tags: List<Tags>?
) : LitePalSupport(), Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readString(),
            parcel.readInt(),
            parcel.readString(),
            parcel.readByte() != 0.toByte(),
            parcel.readInt(),
            parcel.readString(),
            parcel.readString(),
            parcel.readByte() != 0.toByte(),
            parcel.readInt(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readLong(),
            parcel.readInt(),
            parcel.readString(),
            parcel.readString(),
            parcel.readInt(),
            parcel.readInt(),
            parcel.readInt(),
            parcel.readInt(),
            parcel.readLong(),
            parcel.createTypedArrayList(Tags)) {
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
        parcel.writeByte(if (fresh) 1 else 0)
        parcel.writeInt(articleId)
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
        parcel.writeLong(timestamp)
        parcel.writeTypedList(tags)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Article> {
        override fun createFromParcel(parcel: Parcel): Article {
            return Article(parcel)
        }

        override fun newArray(size: Int): Array<Article?> {
            return arrayOfNulls(size)
        }
    }
}

data class Tags(
        var name: String?,
        var url: String?
) : Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readString()) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeString(url)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Tags> {
        override fun createFromParcel(parcel: Parcel): Tags {
            return Tags(parcel)
        }

        override fun newArray(size: Int): Array<Tags?> {
            return arrayOfNulls(size)
        }
    }
}
