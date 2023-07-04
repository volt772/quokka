package com.kakao.data.mapper

import com.kakao.data.di.DefaultDispatcher
import com.kakao.domain.constants.QkdResourceType
import com.kakao.domain.dto.DocumentsRespDto
import com.kakao.domain.dto.QkDocuments
import com.kakao.domain.dto.QkdDocuments
import com.kakao.domain.mapper.QkdDocumentsMapper
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class QkdDocumentsMapperImpl @Inject constructor(
    @DefaultDispatcher private val defaultDispatcher: CoroutineDispatcher,
) : QkdDocumentsMapper {

    override suspend fun mapDocumentToUi(documents: QkdDocuments): QkDocuments {
        return QkDocuments(
            thumbnailUrl = documents.thumbnailUrl?: "",
            thumbnail = documents.thumbnail?: "",
            title = documents.title?: "",
            displaySiteName = documents.displaySiteName?: "",
            datetime = documents.datetime
        )
    }

    override suspend fun mapRespToDocument(page: Int, documents: List<DocumentsRespDto>): List<QkdDocuments> {
        return withContext(defaultDispatcher) {
            documents.map { _d ->
                QkdDocuments(
                    page = page,
                    type = if (_d.thumbnailUrl.isNullOrBlank()) { QkdResourceType.CLIP } else { QkdResourceType.IMAGE},
                    thumbnailUrl = _d.thumbnailUrl?: "",
                    thumbnail = _d.thumbnail?: "",
                    title = _d.title?: "",
                    displaySiteName = _d.displaySiteName?: "",
                    datetime = _d.datetime
                )
            }
        }
    }
}
