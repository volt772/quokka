package com.kakao.data.repository

import androidx.paging.PagingData
import androidx.paging.map
import com.kakao.data.datasource.search.SearchRemoteDataSource
import com.kakao.data.di.DefaultDispatcher
import com.kakao.data.di.IoDispatcher
import com.kakao.domain.mapper.QkdDocumentsMapper
import com.kakao.data.network.QkdApiService
import com.kakao.data.response.QkdResponseRefinery
import com.kakao.domain.dto.QkDocuments
import com.kakao.domain.dto.QkdDocuments
import com.kakao.domain.repository.QkdHamsterRepository
import com.kakao.domain.repository.Resource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.net.UnknownHostException
import javax.inject.Inject

class QkdHamsterRepositoryImpl @Inject constructor(
    @IoDispatcher val ioDispatcher: CoroutineDispatcher,
    @DefaultDispatcher val defaultDispatcher: CoroutineDispatcher,
    private val refinery: QkdResponseRefinery,
    private val api: QkdApiService,
    private val dataSource: SearchRemoteDataSource,
    private val mapper: QkdDocumentsMapper,
): QkdHamsterRepository {

    override suspend fun getHamster() {

        try {
            CoroutineScope(ioDispatcher).launch {
                try {
                    val respImg = withContext(defaultDispatcher) {
                        api.getSearchImage(query = "hamster", page = 1, size = 10, sort = "recency")
                    }.body()?.documents
                    val respClip = withContext(defaultDispatcher) {
                        api.getSearchVClip(query = "hamster", page = 1, size = 10, sort = "recency")
                    }.body()?.documents

                    val searched = mutableListOf<QkdDocuments>().also { _list ->
                        respImg?.let { img -> _list.addAll(img) }
                        respClip?.let { clip -> _list.addAll(clip) }
                    }

                    val aa = searched.map {
                        Resource.Success(it)
                    }

//                    println("probe :: img : ${respImg}")
//                    println("probe :: clip : ${respClip}")
//                    println("probe :: merged : ${aa}")

//                    println("probe :: img : ${respImg.body()}")
//                    println("probe :: clip : ${respClip.body()}")
                } catch (uhe: UnknownHostException) {
                    println("probe :: err :: err : $uhe")
                }
            }
        } catch (e: Exception) {
            println("probe :: err : ${e}")
        }
    }

    override suspend fun documents(query: String): Flow<PagingData<QkDocuments>> {
        return dataSource.search(query)
            .map { pagingData ->
                pagingData.map { docs ->
                    mapper.mapDocumentToUi(docs)
                }
        }
    }
}