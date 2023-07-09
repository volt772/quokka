package com.kakao.domain.dto

import com.google.gson.annotations.SerializedName
import com.kakao.domain.constants.QkdNetworkKeyTags

/**
 * Document
 */
data class Document (

    @field:SerializedName(QkdNetworkKeyTags.Response.THUMBNAIL_URL)
    val thumbnailUrl: String?,

    @field:SerializedName(QkdNetworkKeyTags.Response.THUMBNAIL)
    val thumbnail: String?,

    @field:SerializedName(QkdNetworkKeyTags.Response.TITLE)
    val title: String?,

    @field:SerializedName(QkdNetworkKeyTags.Response.DISPLAY_SITENAME)
    val displaySiteName: String?,

    @field:SerializedName(QkdNetworkKeyTags.Response.DATETIME)
    val datetime: String

)