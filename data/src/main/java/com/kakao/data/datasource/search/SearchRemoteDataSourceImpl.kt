package com.kakao.data.datasource.search

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.kakao.data.di.DefaultDispatcher
import com.kakao.data.di.IoDispatcher
import com.kakao.data.network.ApiService
import com.kakao.domain.constants.QkdConstants.DataSource.PAGING_LOAD_SIZE
import com.kakao.domain.dto.QkdDocuments
import com.kakao.domain.mapper.DocumentsMapper
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * SearchRemoteDataSourceImpl
 * @desc Paging
 * @return Flow<PagingData<QkdDocuments>>
 */
@ExperimentalPagingApi
class SearchRemoteDataSourceImpl @Inject constructor(
    @DefaultDispatcher val ioDispatcher: CoroutineDispatcher,
    private val api: ApiService,
    private val mapper: DocumentsMapper
) : SearchRemoteDataSource {

    override fun search(
        query: String
    ): Flow<PagingData<QkdDocuments>> {

        return Pager(
            config = PagingConfig(
                pageSize = PAGING_LOAD_SIZE,
                enablePlaceholders = true,
                initialLoadSize = PAGING_LOAD_SIZE
            ),
            pagingSourceFactory = {
                SearchRemoteMediator(api, query, ioDispatcher, mapper)
            }
        ).flow
    }
}