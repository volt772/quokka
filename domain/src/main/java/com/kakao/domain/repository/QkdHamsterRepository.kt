package com.kakao.domain.repository

import com.kakao.domain.dto.QkdHamster
import kotlinx.coroutines.flow.Flow

interface QkdHamsterRepository {

//    suspend fun getHamster(): Flow<Resource<List<QkdHamster>>>
    suspend fun getHamster()

}