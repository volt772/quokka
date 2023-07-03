package com.kakao.data.mapper

import com.kakao.data.di.DefaultDispatcher
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
}
