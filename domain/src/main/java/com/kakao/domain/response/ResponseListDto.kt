package com.kakao.domain.response

import com.google.gson.annotations.SerializedName
import com.kakao.domain.constants.QkdConstants

/**
 * @desc Response 리스트 반환
 */

data class ResponseListDto<T> (

    @field:SerializedName(QkdConstants.Response.VALUE)
    val value: List<T>,

    @field:SerializedName(QkdConstants.Response.COUNT)
    var count: Int = 0,

    @field:SerializedName(QkdConstants.Response.OFFSET)
    var offset: Int = 0,

    @field:SerializedName(QkdConstants.Response.LIMIT)
    var limit: Int = 0,

    @field:SerializedName(QkdConstants.Response.TOTAL)
    var total: Int = 0,

)
