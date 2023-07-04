package com.kakao.quokka.model

data class DocumentDto (

    val page: Int = 0,

    val thumbnailUrl: String = "",

    val thumbnail: String = "",

    val title: String = "",

    val displaySiteName: String = "",

    val datetime: String,

    val isFavorite: Boolean?= false

)