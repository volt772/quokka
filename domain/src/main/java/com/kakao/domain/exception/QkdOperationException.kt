package com.kakao.domain.exception

class QkdOperationException(

    val errorCode: Int,

    message: String? = null

) : Exception(" ${message ?: ""} ")
