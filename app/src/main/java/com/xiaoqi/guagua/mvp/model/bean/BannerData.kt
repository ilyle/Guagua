package com.xiaoqi.guagua.mvp.model.bean

data class BannerData(
        var data: List<Banner>?,
        var errorCode: Int,
        var errorMsg: String?
)

data class Banner(
        var desc: String?,
        var id: Int,
        var imagePath: String,
        var isVisible: Int,
        var order: Int,
        var title: String,
        var type: Int,
        var url: String
)