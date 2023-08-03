package com.kakao.domain.repository

import com.kakao.domain.dto.QkdHistory
import kotlinx.coroutines.flow.Flow

interface HistoryRepository {

    suspend fun getKeywords(): Flow<Resource<List<QkdHistory>>>

    suspend fun insert(keyword: String, regDate: Long)

    suspend fun deleteKeyword(keyword: String)

    suspend fun clearKeywords()

}