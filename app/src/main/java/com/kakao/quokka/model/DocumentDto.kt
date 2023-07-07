package com.kakao.quokka.model

import com.kakao.domain.constants.QkdResourceType

/**
 * DocumentDto
 */
data class DocumentDto (

    val key: String = "",

    val page: Int = 0,

    val type: QkdResourceType,

    val thumbnailUrl: String = "",

    val thumbnail: String = "",

    val title: String = "",

    val displaySiteName: String = "",

    val datetime: String,

    var isFavorite: Boolean = false

)