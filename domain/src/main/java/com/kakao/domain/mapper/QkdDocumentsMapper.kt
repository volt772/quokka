package com.kakao.domain.mapper

import com.kakao.domain.dto.QkDocuments
import com.kakao.domain.dto.QkdDocuments

interface QkdDocumentsMapper {

    suspend fun mapDocumentToUi(
        documents: QkdDocuments,
    ): QkDocuments
}