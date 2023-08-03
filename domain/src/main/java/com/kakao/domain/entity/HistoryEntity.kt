package com.kakao.domain.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.kakao.domain.constants.QkdDatabaseTags
import com.kakao.domain.entity.HistoryEntity.Companion.TABLE_HISTORY

@Entity(
    tableName = TABLE_HISTORY,
    indices = [
        Index(value = ["id"], unique = true),
        Index(value = ["keyword"], unique = true)
      ],
)
data class HistoryEntity(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = QkdDatabaseTags.HistoryKey.ID)
    val id: Long = 0,

    @ColumnInfo(name = QkdDatabaseTags.HistoryKey.KEYWORD)
    val keyword: String = "",

    @ColumnInfo(name = QkdDatabaseTags.HistoryKey.REG_DATE)
    val regDate: Long = 0L

) {
    companion object {
        const val TABLE_HISTORY = "history"
    }
}
