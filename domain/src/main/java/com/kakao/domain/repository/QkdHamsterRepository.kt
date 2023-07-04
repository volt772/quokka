package com.kakao.domain.repository

import androidx.paging.PagingData
import com.kakao.domain.dto.QkDocuments
import com.kakao.domain.dto.QkdDocuments
import com.kakao.domain.dto.QkdHamster
import kotlinx.coroutines.flow.Flow

interface QkdHamsterRepository {

//    suspend fun getHamster(): Flow<Resource<List<QkdDocuments>>>
    suspend fun getHamster()

    suspend fun documents(query: String): Flow<PagingData<QkdDocuments>>

}