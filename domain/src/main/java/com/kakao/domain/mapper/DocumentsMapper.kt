package com.kakao.domain.mapper

import com.kakao.domain.dto.Document
import com.kakao.domain.dto.QkdDocuments

interface DocumentsMapper {

    suspend fun mapRespToDocument(
        page: Int,
        documents: List<Document>,
    ): List<QkdDocuments>
}