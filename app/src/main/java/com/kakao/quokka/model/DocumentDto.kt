package com.kakao.quokka.model

import com.kakao.domain.constants.QkdResourceType

data class DocumentDto (

    val page: Int = 0,

    val type: QkdResourceType,

    val thumbnailUrl: String = "",

    val thumbnail: String = "",

    val title: String = "",

    val displaySiteName: String = "",

    val datetime: String,

    val isFavorite: Boolean?= false

)