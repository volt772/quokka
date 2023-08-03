package com.kakao.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.kakao.domain.constants.QkdDatabaseTags
import com.kakao.domain.dto.QkdHistory
import com.kakao.domain.entity.HistoryEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface HistoryDao {

    /* ▼ SELECT ==========================================================================================================================*/
    @Query(
        value =
        """
        SELECT * FROM ${HistoryEntity.TABLE_HISTORY} 
        ORDER BY ${QkdDatabaseTags.HistoryKey.REG_DATE} DESC
        """
    )
    suspend fun getKeywords(): Flow<List<QkdHistory>>

    /* ▼ INSERT ==========================================================================================================================*/
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(historyEntity: HistoryEntity)

    /* ▼ DELETE ==========================================================================================================================*/
    @Query(
        value =
        """
        DELETE FROM ${HistoryEntity.TABLE_HISTORY} 
        WHERE ${QkdDatabaseTags.HistoryKey.KEYWORD} = :keyword
        """
    )
    suspend fun deleteKeyword(keyword: String)

    @Query(
        value =
        """
        DELETE FROM ${HistoryEntity.TABLE_HISTORY} 
        """
    )
    suspend fun clearKeywords()
}
