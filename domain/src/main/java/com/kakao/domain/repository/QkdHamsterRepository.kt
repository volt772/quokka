package com.kakao.domain.repository

import com.kakao.domain.dto.QkdDocuments
import com.kakao.domain.dto.QkdHamster
import kotlinx.coroutines.flow.Flow

interface QkdHamsterRepository {

//    suspend fun getHamster(): Flow<Resource<List<QkdDocuments>>>
    suspend fun getHamster()

}