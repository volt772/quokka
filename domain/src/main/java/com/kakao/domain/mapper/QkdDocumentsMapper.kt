package com.kakao.domain.mapper

import com.kakao.domain.dto.DocumentsRespDto
import com.kakao.domain.dto.QkDocuments
import com.kakao.domain.dto.QkdDocuments

interface QkdDocumentsMapper {

    suspend fun mapDocumentToUi(
        documents: QkdDocuments,
    ): QkDocuments

    suspend fun mapRespToDocument(
        page: Int,
        documents: List<DocumentsRespDto>,
    ): List<QkdDocuments>
}