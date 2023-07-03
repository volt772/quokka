package com.kakao.data.datasource.search

import androidx.paging.PagingData
import com.kakao.domain.dto.QkdDocuments
import kotlinx.coroutines.flow.Flow

interface SearchRemoteDataSource {

    fun search(
        query: String,
    ): Flow<PagingData<QkdDocuments>>
}
