package com.kakao.data.repository

import com.kakao.data.dao.HistoryDao
import com.kakao.domain.dto.QkdHistory
import com.kakao.domain.entity.HistoryEntity
import com.kakao.domain.repository.HistoryRepository
import com.kakao.domain.state.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class HistoryRepositoryImpl @Inject constructor(
    private val historyDao: HistoryDao,
): HistoryRepository {
    override suspend fun getKeywords(): Flow<Resource<List<QkdHistory>>> {
        return historyDao.getKeywords().map { Resource.Success(it) }
    }

    override suspend fun insert(keyword: String, regDate: Long): Boolean {
        val keywordId = historyDao.getKeywordId(keyword)

        return historyDao.insertOrUpdate(HistoryEntity(
            id = keywordId,
            keyword = keyword,
            regDate = regDate
        )) > 0
    }

    override suspend fun deleteKeyword(kid: Long) {
        historyDao.deleteKeyword(kid)
    }

    override suspend fun clearKeywords() {
        historyDao.clearKeywords()
    }

}