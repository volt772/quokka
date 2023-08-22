package com.kakao.data.dao

import androidx.room.Dao
import androidx.room.Query
import com.kakao.domain.constants.QkdDatabaseTags
import com.kakao.domain.dto.QkdHistory
import com.kakao.domain.entity.HistoryEntity
import kotlinx.coroutines.flow.Flow

@Dao
abstract class HistoryDao: BaseDao<HistoryEntity>() {

    /* ▼ SELECT ==========================================================================================================================*/
    @Query(
        value =
        """
        SELECT * FROM ${HistoryEntity.TABLE_HISTORY} 
        ORDER BY ${QkdDatabaseTags.HistoryKey.REG_DATE} DESC
        """
    )
    abstract fun getKeywords(): Flow<List<QkdHistory>>

    @Query(
        value =
        """
        SELECT ${QkdDatabaseTags.HistoryKey.ID} FROM ${HistoryEntity.TABLE_HISTORY} 
        WHERE ${QkdDatabaseTags.HistoryKey.KEYWORD} = :keyword
        """
    )
    abstract fun getKeywordId(keyword: String): Long

    /* ▼ INSERT ==========================================================================================================================*/
//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    abstract fun insert(historyEntity: HistoryEntity)

    /* ▼ DELETE ==========================================================================================================================*/
    @Query(
        value =
        """
        DELETE FROM ${HistoryEntity.TABLE_HISTORY} 
        WHERE ${QkdDatabaseTags.HistoryKey.ID} = :kid
        """
    )
    abstract fun deleteKeyword(kid: Long)

    @Query(
        value =
        """
        DELETE FROM ${HistoryEntity.TABLE_HISTORY} 
        """
    )
    abstract fun clearKeywords()
}
