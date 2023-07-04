package com.kakao.domain.dto

import com.kakao.domain.constants.QkdResourceType

data class QkdDocuments (

    val page: Int,

    val type: QkdResourceType,

    val thumbnailUrl: String,

    val thumbnail: String,

    val title: String,

    val displaySiteName: String,

    val datetime: String

)