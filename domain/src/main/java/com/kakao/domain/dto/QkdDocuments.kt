package com.kakao.domain.dto

import com.google.gson.annotations.SerializedName
import com.kakao.domain.constants.QkdNetworkKeyTags
import com.kakao.domain.constants.QkdResourceType

/**
 * QkdDocuments
 */
data class QkdDocuments (

    val page: Int,

    val type: QkdResourceType,

    val thumbnailUrl: String,

    val thumbnail: String,

    val title: String,

    val displaySiteName: String,

    val datetime: String,

    val imageUrl: String,

    val url: String,

)