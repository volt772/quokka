package com.kakao.quokka.ui.search

import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.insertSeparators
import androidx.paging.map
import com.apx6.chipmunk.app.ui.base.BaseViewModel
import com.kakao.domain.repository.QkdHamsterRepository
import com.kakao.quokka.di.IoDispatcher
import com.kakao.quokka.mapper.DocumentsMapper
import com.kakao.quokka.model.DocumentModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    @IoDispatcher val ioDispatcher: CoroutineDispatcher,
    private val repository: QkdHamsterRepository,
    private val mapper: DocumentsMapper
) : BaseViewModel() {

    private val _query: MutableSharedFlow<String> = MutableSharedFlow()
    val query: SharedFlow<String> = _query

    suspend fun queryDocuments(query: String) {
        viewModelScope.launch {
            _query.emit(query)
        }
    }

    suspend fun getDocuments(query: String): Flow<PagingData<DocumentModel>> {
        return repository.documents(query)
            .map { pagingData ->
                pagingData.map {
                    DocumentModel.DocumentItem(mapper.mapDocumentToUi(it))
                }
            }
            .map {
                it.insertSeparators { before, after ->
                    if (before?.doc?.page != after?.doc?.page) {
                        DocumentModel.SeparatorItem("${after?.doc?.page}")
                    } else {
                        null
                    }
                }
            }
            .cachedIn(viewModelScope)
    }
}