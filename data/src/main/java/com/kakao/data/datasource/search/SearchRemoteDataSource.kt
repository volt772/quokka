package com.kakao.data.datasource.search

import androidx.paging.PagingData
import com.kakao.domain.dto.QkdDocuments
import kotlinx.coroutines.flow.Flow

/**
 * SearchRemoteDataSource
 * @desc Paging
 * @return Flow<PagingData<QkdDocuments>>
 */
interface SearchRemoteDataSource {
    fun search(
        query: String,
    ): Flow<PagingData<QkdDocuments>>
}
