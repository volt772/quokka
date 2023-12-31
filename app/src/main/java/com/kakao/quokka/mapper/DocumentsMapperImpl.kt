package com.kakao.quokka.mapper

import com.kakao.domain.constants.QkdResourceType
import com.kakao.domain.dto.QkdDocuments
import com.kakao.quokka.constants.QkConstants
import com.kakao.quokka.ext.retrieveFileKey
import com.kakao.quokka.ext.splitKey
import com.kakao.quokka.model.DocumentDto
import com.kakao.quokka.preference.PrefManager
import javax.inject.Inject

/**
 * DocumentsMapperImpl
 */
class DocumentsMapperImpl @Inject constructor(
    private val prefManager: PrefManager,
) : DocumentsMapper {

    override suspend fun mapDocumentToUi(documents: QkdDocuments): DocumentDto {
        val url = if (documents.type == QkdResourceType.IMAGE) {
            documents.thumbnailUrl
        } else {
            documents.thumbnail
        }

        /* check -> is favorite image*/
        val fileKey = url.retrieveFileKey()

        val favorsSet = prefManager.getStringSet(QkConstants.Pref.FAVORITE_KEY)

        var isKeyExists = false
        favorsSet.forEach { f ->
            val keySet = f.splitKey()
            val dUrl = keySet.first
            if (dUrl == url) {
                isKeyExists = true
                return@forEach
            }
        }

        return DocumentDto(
            key = fileKey,
            page = documents.page,
            type = documents.type,
            thumbnailUrl = documents.thumbnailUrl,
            thumbnail = documents.thumbnail,
            title = documents.title,
            displaySiteName = documents.displaySiteName,
            datetime = documents.datetime,
            isFavorite = isKeyExists
        )
    }
}
