package com.kakao.domain.repository

import androidx.paging.PagingData
import com.kakao.domain.dto.QkdDocuments
import kotlinx.coroutines.flow.Flow

interface DocumentsRepository {

    suspend fun documents(query: String): Flow<PagingData<QkdDocuments>>

}