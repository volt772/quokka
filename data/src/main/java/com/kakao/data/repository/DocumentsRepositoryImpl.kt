package com.kakao.data.repository

import androidx.paging.PagingData
import com.kakao.data.datasource.search.SearchRemoteDataSource
import com.kakao.domain.dto.QkdDocuments
import com.kakao.domain.repository.DocumentsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DocumentsRepositoryImpl @Inject constructor(
    private val dataSource: SearchRemoteDataSource,
): DocumentsRepository {

    override suspend fun documents(query: String): Flow<PagingData<QkdDocuments>> {
        return dataSource.search(query)
    }
}