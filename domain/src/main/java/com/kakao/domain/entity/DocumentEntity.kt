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
    @ColumnInfo(name = QkdDatabaseTags.DocumentKey.ID)
    val id: Long = 0,

    @ColumnInfo(name = QkdDatabaseTags.DocumentKey.KEY)
    val key: String = "",

    @ColumnInfo(name = QkdDatabaseTags.DocumentKey.THUMBNAIL_URL)
    val thumbnailUrl: String = "",

    @ColumnInfo(name = QkdDatabaseTags.DocumentKey.THUMBNAIL)
    val thumbnail: String = "",

    @ColumnInfo(name = QkdDatabaseTags.DocumentKey.TITLE)
    val title: String = "",

    @ColumnInfo(name = QkdDatabaseTags.DocumentKey.DISPLAY_SITE_NAME)
    val displaySiteName: String = "",

    @ColumnInfo(name = QkdDatabaseTags.DocumentKey.DATETIME)
    val datetime: String = "",

    @ColumnInfo(name = QkdDatabaseTags.DocumentKey.IMAGE_URL)
    val imageUrl: String = "",

    @ColumnInfo(name = QkdDatabaseTags.DocumentKey.URL)
    val url: String = ""

) {
    companion object {
        const val TABLE_DOCUMENT = "document"
    }
}
