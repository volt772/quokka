package com.kakao.domain.dto

import com.google.gson.annotations.SerializedName
import com.kakao.domain.constants.QkdNetworkKeyTags

data class QkdDocuments (

    @field:SerializedName(QkdNetworkKeyTags.Response.THUMBNAIL_URL)
    val thumbnailUrl: String,

    @field:SerializedName(QkdNetworkKeyTags.Response.THUMBNAIL)
    val thumbnail: String,

    @field:SerializedName(QkdNetworkKeyTags.Response.TITLE)
    val title: String,

    @field:SerializedName(QkdNetworkKeyTags.Response.DISPLAY_SITENAME)
    val displaySiteName: String,

    @field:SerializedName(QkdNetworkKeyTags.Response.DATETIME)
    val datetime: String

//    val collection:	String,
//
//    val thumbnail_url: String,
//
//    val image_url: String,
//
//    val width: Int,
//
//    val height: Int,
//
//    val display_sitename: String,
//
//    val doc_url: String,
//
//    val title: String,
//
//    val url: String,
//
//    val play_time: Int,
//
//    val thumbnail: String,
//
//    val author: String,
//
//    val datetime: String

)