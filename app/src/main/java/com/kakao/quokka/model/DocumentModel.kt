package com.kakao.quokka.model

/**
 * DocumentModel
 * @desc Search Result Dto
 * @desc Item & Separator
 */
sealed class DocumentModel {

    data class DocumentItem(val doc: DocumentDto) : DocumentModel()

    data class SeparatorItem(val desc: String) : DocumentModel()

}