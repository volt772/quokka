package com.kakao.data.repository

import com.kakao.data.dao.HistoryDao
import com.kakao.domain.dto.QkdHistory
import com.kakao.domain.entity.HistoryEntity
import com.kakao.domain.repository.HistoryRepository
import com.kakao.domain.repository.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class HistoryRepositoryImpl @Inject constructor(
    private val historyDao: HistoryDao,
): HistoryRepository {
    override suspend fun getKeywords(): Flow<Resource<List<QkdHistory>>> {
        val keywords = historyDao.getKeywords()
        return keywords.map { Resource.Success(it) }
    }

    override suspend fun insert(keyword: String, regDate: Long) {
        return historyDao.insert(HistoryEntity(
            keyword = keyword,
            regDate = regDate
        ))
    }

    override suspend fun deleteKeyword(keyword: String) {
        historyDao.deleteKeyword(keyword)
    }

    override suspend fun clearKeywords() {
        historyDao.clearKeywords()
    }

}