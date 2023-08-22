package com.kakao.domain.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.kakao.domain.constants.QkdDatabaseTags
import com.kakao.domain.entity.DocumentEntity.Companion.TABLE_DOCUMENT

@Entity(
    tableName = TABLE_DOCUMENT,
    indices = [
        Index(value = ["id"], unique = true),
        Index(value = ["key"], unique = true),
  ],
)
data class DocumentEntity(

    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,

    val key: String = "",

    val thumbnailUrl: String = "",

    val thumbnail: String = "",

    val title: String = "",

    val displaySiteName: String = "",

    val datetime: String = "",

    val imageUrl: String = "",

    val url: String = ""

) {
    companion object {
        const val TABLE_DOCUMENT = "document"
    }
}
