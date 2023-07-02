package com.kakao.data.response

import com.kakao.domain.response.QkdErrorResponse
import com.kakao.domain.response.QkdSuccessResponse

sealed class QkdResult<out T : Any> {

    data class Success<out T : Any>(val data: QkdSuccessResponse<*>) : QkdResult<T>()

    data class Error<out T : Any>(val exception: QkdErrorResponse) : QkdResult<T>()

}
