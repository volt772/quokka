package com.kakao.quokka.mapper

import com.kakao.domain.dto.QkdDocuments
import com.kakao.quokka.model.DocumentDto

/**
 * DocumentsMapper
 * @desc mapping 'QkdDocuments' to 'DocumentDto'
 */
interface DocumentsMapper {

    suspend fun mapDocumentToUi(
        documents: QkdDocuments,
    ): DocumentDto

}