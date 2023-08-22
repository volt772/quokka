package com.kakao.quokka.model

/**
 * History
 * @desc Recent Searched Keyword
 */
data class HistoryModel (

    val id: Long,

    val keyword: String,

    val regDate: Long

)