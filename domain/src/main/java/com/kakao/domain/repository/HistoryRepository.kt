package com.kakao.domain.repository

import com.kakao.domain.dto.QkdHistory
import com.kakao.domain.state.Resource
import kotlinx.coroutines.flow.Flow

interface HistoryRepository {

    suspend fun getKeywords(): Flow<Resource<List<QkdHistory>>>

    suspend fun insert(keyword: String, regDate: Long): Boolean

    suspend fun deleteKeyword(kid: Long)

    suspend fun clearKeywords()

}