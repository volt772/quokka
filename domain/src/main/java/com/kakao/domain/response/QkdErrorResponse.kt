package com.kakao.domain.response

import com.kakao.domain.exception.ErrorDto

/**
 * @desc Response 실패 객체
 */

data class QkdErrorResponse(

    var error: ErrorDto? = null

)
