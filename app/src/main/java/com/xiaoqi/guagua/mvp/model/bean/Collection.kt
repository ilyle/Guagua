package com.xiaoqi.guagua.mvp.model.bean

import org.litepal.crud.LitePalSupport

class Collection : LitePalSupport(){
    var id: Int = 0
    var link: String? = null
    var userId: Int = 0
    var title: String? = null
    var author: String? = null
    var chapterId: Int = 0
    var chapterName: String? = null
    var timestamp: Long = 0

    companion object {
        fun build(essay: EssayData.Data.Essay): Collection{
            val collection = Collection()
            collection.id = essay.id
            collection.link = essay.link
            collection.userId = essay.userId
            collection.title = essay.title
            collection.author = essay.author
            collection.chapterId = essay.chapterId
            collection.chapterName = essay.chapterName
            return collection
        }
    }
}