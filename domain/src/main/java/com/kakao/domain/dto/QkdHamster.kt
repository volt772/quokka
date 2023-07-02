package com.kakao.domain.dto

import com.google.gson.annotations.SerializedName
import com.kakao.domain.constants.QkdNetworkKeyTags

data class QkdHamster (

    @field:SerializedName(QkdNetworkKeyTags.Response.META)
    val meta: QkdMeta,

    @field:SerializedName(QkdNetworkKeyTags.Response.DOCUMENTS)
    val documents: List<QkdDocuments>

)