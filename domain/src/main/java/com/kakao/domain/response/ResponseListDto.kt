package com.kakao.domain.response

import com.google.gson.annotations.SerializedName
import com.kakao.domain.constants.QkdNetworkKeyTags
import com.kakao.domain.dto.QkdMeta

/**
 * @desc Response 리스트 반환
 */

data class ResponseListDto<T> (

    @field:SerializedName(QkdNetworkKeyTags.Response.META)
    val meta: QkdMeta,

    @field:SerializedName(QkdNetworkKeyTags.Response.DOCUMENTS)
    var documents: List<T>

)
