package com.kakao.data.mapper

import com.kakao.data.di.DefaultDispatcher
import com.kakao.domain.constants.QkdResourceType
import com.kakao.domain.dto.Document
import com.kakao.domain.dto.QkdDocuments
import com.kakao.domain.mapper.DocumentsMapper
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * DocumentsMapperImpl
 */
class DocumentsMapperImpl @Inject constructor(
    @DefaultDispatcher private val defaultDispatcher: CoroutineDispatcher,
) : DocumentsMapper {

    override suspend fun mapRespToDocument(
        page: Int,
        documents: List<Document>)
    : List<QkdDocuments> {
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
