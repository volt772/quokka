package com.kakao.domain.response

/**
 * @desc Response 성공 객체
 */

data class QkdSuccessResponse<T>(

    val code: Int,

    val body: T?,

//    val nextOffset: Int? = null,
//
//    val limit: Int? = null

)
