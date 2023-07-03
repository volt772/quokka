package com.kakao.data.datasource.search

import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.kakao.data.network.QkdApiService
import com.kakao.domain.dto.QkdDocuments
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException

private const val TMDB_STARTING_PAGE_INDEX = 1

@ExperimentalPagingApi
class SearchRemoteMediator(
    private val api: QkdApiService,
    private val query: String,
    private val ioDispatcher: CoroutineDispatcher,
    private val defaultDispatcher: CoroutineDispatcher,
) : PagingSource<Int, QkdDocuments>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, QkdDocuments> {
        val pageIndex = params.key ?: TMDB_STARTING_PAGE_INDEX
        return try {
//            val response = service.getTopRatedMovies(
//                language = "en-US",
//                page = pageIndex
//            )
            val respImg = withContext(defaultDispatcher) {
                api.getSearchImage(query = "hamster", page = pageIndex, size = 10, sort = "recency")
            }.body()?.documents
            val respClip = withContext(defaultDispatcher) {
                api.getSearchVClip(query = "hamster", page = pageIndex, size = 10, sort = "recency")
            }.body()?.documents

            val searched = mutableListOf<QkdDocuments>().also { _list ->
                respImg?.let { img -> _list.addAll(img) }
                respClip?.let { clip -> _list.addAll(clip) }
            }

            val movies = searched
            println("probe :: movies : $movies")
            val nextKey =
                if (movies.isEmpty()) {
                    null
                } else {
                    // By default, initial load size = 3 * NETWORK PAGE SIZE
                    // ensure we're not requesting duplicating items at the 2nd request
                    pageIndex + (params.loadSize / 10)
                }
            LoadResult.Page(
                data = movies,
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
        // We need to get the previous key (or next key if previous is null) of the page
        // that was closest to the most recently accessed index.
        // Anchor position is the most recently accessed index.
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

//    override suspend fun initialize(): InitializeAction {
//        return InitializeAction.LAUNCH_INITIAL_REFRESH
//    }
//
//    override suspend fun load(
//        loadType: LoadType,
//        state: PagingState<Int, QkdDocuments>
//    ): MediatorResult {
//        val page = when (val pageKeyData = getKeyPageData(loadType, state)) {
//            is MediatorResult.Success -> {
//                return pageKeyData
//            }
//            else -> {
//                pageKeyData as Int
//            }
//        }
//
//        try {
//            val response = api.fetchMediatorCards(
//                spaceId = ids.spaceId,
//                boardId = ids.boardId,
//                sectionId = ids.sectionId,
//                offset = page,
//                limit = state.config.pageSize
//            )
//
//            val isEndOfList = response.value.isEmpty()
//            mpDatabase.withTransaction {
//                if (loadType == LoadType.REFRESH) {
//                    mpDatabase.cardsDao().deleteCardsInSection(sectionId = ids.sectionRowId)
//                    operator.deleteRemoteKeys(MpdRemoteKeysParam(typeName = MpdRemoteKeyType.CARD, extId1 = ids.sectionRowId))
//                }
//
//                val accountId = mpDatabase.accountDao().getAccountId() ?: 0L
//                val prevKey = if (page == STARTING_PAGE_INDEX) null else page - 1
//                val nextKey = if (isEndOfList) null else page + 1
//                val keys = response.value.map {
//                    RemoteKeyEntity(
//                        accountId = accountId,
//                        typeName = MpdRemoteKeyType.CARD.key,
//                        typeId = it.cardId,
//                        prevKey = prevKey,
//                        nextKey = nextKey,
//                        extId1 = ids.sectionRowId
//                    )
//                }
//
//                operator.insertRemoteKeys(keys)
//                mpDatabase.cardsDao().insertOrUpdate(
//                    cardMapper.mapCardPagingToDB(response.value, ids.sectionRowId)
//                )
//            }
//            return MediatorResult.Success(endOfPaginationReached = isEndOfList)
//        } catch (exception: IOException) {
//            return MediatorResult.Error(exception)
//        } catch (exception: HttpException) {
//            return MediatorResult.Error(exception)
//        }
//    }
//
////    private suspend fun getKeyPageData(loadType: LoadType, state: PagingState<Int, QkdDocuments>): Any {
////        return when (loadType) {
////            LoadType.REFRESH -> {
////                val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
////                remoteKeys?.nextKey?.minus(1) ?: STARTING_PAGE_INDEX
////            }
////            LoadType.APPEND -> {
////                val remoteKeys = getLastRemoteKey(state)
////                val nextKey = remoteKeys?.nextKey
////                return nextKey ?: MediatorResult.Success(endOfPaginationReached = false)
////            }
////            LoadType.PREPEND -> {
////                val remoteKeys = getFirstRemoteKey(state)
////                val prevKey = remoteKeys?.prevKey ?: return MediatorResult.Success(
////                    endOfPaginationReached = false
////                )
////                prevKey
////            }
////        }
////    }
//
////    private suspend fun getRemoteKeyClosestToCurrentPosition(state: PagingState<Int, QkdDocuments>): RemoteKeyEntity? {
////        return state.anchorPosition?.let { position ->
////            state.closestItemToPosition(position)?.cardId?.let { repoId ->
////                operator.getRemoteKeyClosestToCurrentPosition(
////                    MpdRemoteKeysParam(
////                        typeName = MpdRemoteKeyType.CARD,
////                        typeId = repoId,
////                        extId1 = ids.sectionRowId
////                    )
////                )
////            }
////        }
////    }
//
////    private suspend fun getLastRemoteKey(state: PagingState<Int, QkdDocuments>): RemoteKeyEntity? {
////        return state.pages
////            .lastOrNull { it.data.isNotEmpty() }
////            ?.data?.lastOrNull()
////            ?.let { card ->
////                operator.getLastRemoteKey(
////                    MpdRemoteKeysParam(
////                        typeName = MpdRemoteKeyType.CARD,
////                        typeId = card.cardId,
////                        extId1 = ids.sectionRowId
////                    )
////                )
////            }
////    }
//
////    private suspend fun getFirstRemoteKey(state: PagingState<Int, QkdDocuments>): RemoteKeyEntity? {
////        return state.pages
////            .firstOrNull { it.data.isNotEmpty() }
////            ?.data?.firstOrNull()
////            ?.let { card ->
////                operator.getFirstRemoteKey(
////                    MpdRemoteKeysParam(
////                        typeName = MpdRemoteKeyType.CARD,
////                        typeId = card.cardId,
////                        extId1 = ids.sectionRowId
////                    )
////                )
////            }
////    }
}
