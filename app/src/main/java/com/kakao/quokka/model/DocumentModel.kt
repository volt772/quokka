package com.kakao.quokka.model

sealed class DocumentModel {

    data class DocumentItem(val doc: DocumentDto) : DocumentModel()

    data class SeparatorItem(val desc: String) : DocumentModel()

}