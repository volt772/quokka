package com.kakao.domain.dto

import com.google.gson.annotations.SerializedName
import com.kakao.domain.constants.QkdNetworkKeyTags

/**
 * @desc Response 리스트 반환
 */

data class ResponseList<T> (

    @field:SerializedName(QkdNetworkKeyTags.Response.META)
    val meta: QkdMeta,

    @field:SerializedName(QkdNetworkKeyTags.Response.DOCUMENTS)
    var documents: List<T>

)
