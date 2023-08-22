package com.kakao.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.kakao.domain.constants.QkdDatabaseTags
import com.kakao.domain.entity.DocumentEntity
import com.kakao.domain.entity.RemoteKeyEntity

@Dao
abstract class DocumentDao: BaseDao<DocumentEntity>() {

    /* ▼ SELECT ==========================================================================================================================*/
    @Query(
        value =
        """
        SELECT * FROM ${RemoteKeyEntity.TABLE_REMOTE_KEYS} 
        WHERE `${QkdDatabaseTags.RemoteKey.KEY}` = :key 
        """
    )
    abstract fun getRemoteKey(
        key: String
    ): RemoteKeyEntity?

    /* ▼ INSERT ==========================================================================================================================*/
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertAll(remoteKeyEntity: List<RemoteKeyEntity>)

    /* ▼ DELETE ==========================================================================================================================*/

    @Query(
        value =
        """
        DELETE FROM ${RemoteKeyEntity.TABLE_REMOTE_KEYS} 
        WHERE `${QkdDatabaseTags.RemoteKey.KEY}` = :key
        """
    )
    abstract fun deleteKeys(
        key: String
    )
}
