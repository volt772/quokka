package com.kakao.quokka.mapper

import com.kakao.domain.dto.QkdDocuments
import com.kakao.quokka.model.DocumentDto

interface DocumentsMapper {

    suspend fun mapDocumentToUi(
        documents: QkdDocuments,
    ): DocumentDto
}