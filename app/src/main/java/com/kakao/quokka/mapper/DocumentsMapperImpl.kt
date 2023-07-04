package com.kakao.quokka.mapper

import com.kakao.data.di.DefaultDispatcher
import com.kakao.domain.dto.QkdDocuments
import com.kakao.quokka.model.DocumentDto
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class DocumentsMapperImpl @Inject constructor(
    @DefaultDispatcher private val defaultDispatcher: CoroutineDispatcher,
) : DocumentsMapper {

    override suspend fun mapDocumentToUi(documents: QkdDocuments): DocumentDto {
        return DocumentDto(
            page = documents.page,
            thumbnailUrl = documents.thumbnailUrl?: "",
            thumbnail = documents.thumbnail?: "",
            title = documents.title?: "",
            displaySiteName = documents.displaySiteName?: "",
            datetime = documents.datetime
        )
    }
}
