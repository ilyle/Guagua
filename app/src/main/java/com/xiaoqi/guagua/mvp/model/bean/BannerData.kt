package com.xiaoqi.guagua.mvp.model.bean

import android.os.Parcel
import android.os.Parcelable

data class BannerData(
        var data: List<Banner>?,
        var errorCode: Int,
        var errorMsg: String?
)

data class Banner(
        var desc: String?,
        var id: Int,
        var imagePath: String?,
        var isVisible: Int,
        var order: Int,
        var title: String?,
        var type: Int,
        var url: String?
) : Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readInt(),
            parcel.readString(),
            parcel.readInt(),
            parcel.readInt(),
            parcel.readString(),
            parcel.readInt(),
            parcel.readString()) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(desc)
        parcel.writeInt(id)
        parcel.writeString(imagePath)
        parcel.writeInt(isVisible)
        parcel.writeInt(order)
        parcel.writeString(title)
        parcel.writeInt(type)
        parcel.writeString(url)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Banner> {
        override fun createFromParcel(parcel: Parcel): Banner {
            return Banner(parcel)
        }

        override fun newArray(size: Int): Array<Banner?> {
            return arrayOfNulls(size)
        }
    }
}