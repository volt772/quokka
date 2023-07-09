package com.kakao.data.datasource.search

import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.kakao.data.di.IoDispatcher
import com.kakao.data.network.ApiService
import com.kakao.domain.constants.QkdConstants.DataSource.PAGING_NETWORK_SIZE
import com.kakao.domain.constants.QkdConstants.DataSource.PAGING_NETWORK_SORT
import com.kakao.domain.dto.QkdDocuments
import com.kakao.domain.mapper.DocumentsMapper
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException

/**
 * SearchRemoteMediator
 * @desc Paging Request & Arrange Data Set
 * @return Flow<PagingData<QkdDocuments>>
 */

private const val TMDB_STARTING_PAGE_INDEX = 1

@ExperimentalPagingApi
class SearchRemoteMediator(
    private val api: ApiService,
    private val query: String,
    private val ioDispatcher: CoroutineDispatcher,
    private val mapper: DocumentsMapper
) : PagingSource<Int, QkdDocuments>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, QkdDocuments> {
        val pageIndex = params.key ?: TMDB_STARTING_PAGE_INDEX
        return try {

            val respImg = withContext(ioDispatcher) {
                api.getSearchImage(query = query, page = pageIndex, size = PAGING_NETWORK_SIZE, sort = PAGING_NETWORK_SORT)
            }.body()?.documents
            val respClip = withContext(ioDispatcher) {
                api.getSearchVClip(query = query, page = pageIndex, size = PAGING_NETWORK_SIZE, sort = PAGING_NETWORK_SORT)
            }.body()?.documents

            val docs = mutableListOf<QkdDocuments>().also { _list ->
                respImg?.let { img -> _list.addAll(mapper.mapRespToDocument(pageIndex, img)) }
                respClip?.let { clip -> _list.addAll(mapper.mapRespToDocument(pageIndex, clip)) }
            }

            docs.sortByDescending { it.datetime }

            val nextKey = if (docs.isEmpty()) {
                    null
                } else {
                    pageIndex + (params.loadSize / 10)
                }
            LoadResult.Page(
                data = docs,
                prevKey = if (pageIndex == TMDB_STARTING_PAGE_INDEX) null else pageIndex,
                nextKey = nextKey
            )
        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            return LoadResult.Error(exception)
        }
    }

    /**
     * The refresh key is used for subsequent calls to PagingSource.Load after the initial load.
     */
    override fun getRefreshKey(state: PagingState<Int, QkdDocuments>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }
}
