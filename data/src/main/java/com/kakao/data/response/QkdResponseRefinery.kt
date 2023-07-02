package com.kakao.data.response

import retrofit2.Response

interface QkdResponseRefinery {

    fun <T> response(response: Response<T>): QkdResult<Any>
}
