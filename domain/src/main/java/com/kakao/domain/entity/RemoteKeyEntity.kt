package com.kakao.domain.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.kakao.domain.constants.QkdDatabaseTags
import com.kakao.domain.entity.RemoteKeyEntity.Companion.TABLE_REMOTE_KEYS

@Entity(
    tableName = TABLE_REMOTE_KEYS,
    indices = [Index(value = ["id"], unique = true)],
)
data class RemoteKeyEntity(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = QkdDatabaseTags.RemoteKey.ID)
    val id: Long = 0,

    @ColumnInfo(name = QkdDatabaseTags.RemoteKey.KEY)
    val key: String = "",

    @ColumnInfo(name = QkdDatabaseTags.RemoteKey.PREV_KEY)
    val prevKey: Int?,

    @ColumnInfo(name = QkdDatabaseTags.RemoteKey.NEXT_KEY)
    val nextKey: Int?

) {
    companion object {
        const val TABLE_REMOTE_KEYS = "remote_keys"
    }
}
