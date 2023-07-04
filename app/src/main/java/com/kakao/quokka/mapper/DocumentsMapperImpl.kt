package com.kakao.quokka.mapper

import com.kakao.data.di.DefaultDispatcher
import com.kakao.domain.constants.QkdResourceType
import com.kakao.domain.dto.QkdDocuments
import com.kakao.quokka.ext.retrieveFileKey
import com.kakao.quokka.model.DocumentDto
import com.kakao.quokka.preference.QkPreference
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class DocumentsMapperImpl @Inject constructor(
    private val qkPreference: QkPreference,
    @DefaultDispatcher private val defaultDispatcher: CoroutineDispatcher,
) : DocumentsMapper {

    override suspend fun mapDocumentToUi(documents: QkdDocuments): DocumentDto {
        val url = if (documents.type == QkdResourceType.IMAGE) {
            documents.thumbnailUrl
        } else {
            documents.thumbnail
        }

        val fileKey = url.retrieveFileKey()
        val isKeyExists = qkPreference.getFileKey(fileKey)

        return DocumentDto(
            key = fileKey,
            page = documents.page,
            type = documents.type,
            thumbnailUrl = documents.thumbnailUrl?: "",
            thumbnail = documents.thumbnail?: "",
            title = documents.title?: "",
            displaySiteName = documents.displaySiteName?: "",
            datetime = documents.datetime,
            isFavorite = isKeyExists
        )
    }
}
