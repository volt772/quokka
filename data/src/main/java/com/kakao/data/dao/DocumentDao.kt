package com.kakao.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.kakao.domain.constants.QkdDatabaseTags
import com.kakao.domain.entity.RemoteKeyEntity

@Dao
interface DocumentDao {

    /* ▼ SELECT ==========================================================================================================================*/
    @Query(
        value =
        """
        SELECT * FROM ${RemoteKeyEntity.TABLE_REMOTE_KEYS} 
        WHERE ${QkdDatabaseTags.RemoteKey.KEY} = :key 
        """
    )
    suspend fun getRemoteKey(
        id: Long,
        key: String
    ): RemoteKeyEntity?

    /* ▼ INSERT ==========================================================================================================================*/
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(remoteKeyEntity: List<RemoteKeyEntity>)

    /* ▼ DELETE ==========================================================================================================================*/

    @Query(
        value =
        """
        DELETE FROM ${RemoteKeyEntity.TABLE_REMOTE_KEYS} 
        WHERE ${QkdDatabaseTags.RemoteKey.KEY} = :key
        """
    )
    suspend fun deleteKeys(
        id: Long,
        key: String
    )
}
