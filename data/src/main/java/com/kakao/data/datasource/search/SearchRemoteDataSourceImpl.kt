package com.kakao.data.datasource.search

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.kakao.data.di.DefaultDispatcher
import com.kakao.data.di.IoDispatcher
import com.kakao.data.network.QkdApiService
import com.kakao.domain.constants.QkdConstants.DataSource.PAGING_LOAD_SIZE
import com.kakao.domain.dto.QkdDocuments
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@ExperimentalPagingApi
class SearchRemoteDataSourceImpl @Inject constructor(
    @IoDispatcher val ioDispatcher: CoroutineDispatcher,
    @DefaultDispatcher val defaultDispatcher: CoroutineDispatcher,
    private val api: QkdApiService
) : SearchRemoteDataSource {

    override fun search(
        query: String
    ): Flow<PagingData<QkdDocuments>> {

        return Pager(
            config = PagingConfig(
                pageSize = PAGING_LOAD_SIZE,
                enablePlaceholders = false,
                initialLoadSize = PAGING_LOAD_SIZE * 2
            ),
            pagingSourceFactory = {
                SearchRemoteMediator(api, query, ioDispatcher, defaultDispatcher)
            }
        ).flow
    }
}