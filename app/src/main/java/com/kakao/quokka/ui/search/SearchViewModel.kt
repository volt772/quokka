package com.kakao.quokka.ui.search

import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.insertSeparators
import androidx.paging.map
import com.kakao.domain.dto.QkdHistory
import com.kakao.domain.repository.DocumentsRepository
import com.kakao.domain.repository.HistoryRepository
import com.kakao.domain.state.State
import com.kakao.quokka.constants.QkConstants
import com.kakao.quokka.di.IoDispatcher
import com.kakao.quokka.ext.currMillis
import com.kakao.quokka.ext.splitKey
import com.kakao.quokka.mapper.DocumentsMapper
import com.kakao.quokka.model.DocumentModel
import com.kakao.quokka.model.HistoryModel
import com.kakao.quokka.preference.PrefManager
import com.kakao.quokka.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject


/**
 * SearchViewModel
 */
@HiltViewModel
class SearchViewModel @Inject constructor(
    @IoDispatcher val ioDispatcher: CoroutineDispatcher,
    private val prefManager: PrefManager,
    private val documentRepository: DocumentsRepository,
    private val historyRepository: HistoryRepository,
    private val mapper: DocumentsMapper
) : BaseViewModel() {

    /* Search Keyword*/
    private val _query: MutableSharedFlow<String> = MutableSharedFlow()
    val query: SharedFlow<String> = _query

    private val _keywords: MutableStateFlow<State<List<QkdHistory>>> = MutableStateFlow(State.loading())
    val keywords: StateFlow<State<List<QkdHistory>>> = _keywords

    /**
     * Search Keyword
     * @param query Keyword
     */
    suspend fun queryDocuments(query: String) {
        viewModelScope.launch {
            _query.emit(query)
        }
    }

    /**
     * Request by keyword
     * @param query Keyword
     */
    suspend fun getDocuments(query: String): Flow<PagingData<DocumentModel>> {
        return documentRepository.documents(query)
            .map { pagingData ->
                pagingData.map {
                    DocumentModel.DocumentItem(mapper.mapDocumentToUi(it))
                }
            }
            .map {
                it.insertSeparators { before, after ->
                    if (before?.doc?.page != after?.doc?.page) {
                        val label = after?.doc?.page
                        label?.let { _label ->
                            DocumentModel.SeparatorItem(_label.toString())
                        }
                    } else {
                        null
                    }
                }
            }
            .cachedIn(viewModelScope)
    }

    /**
     * History List
     * @desc emit history model -> HistoryModel()
     */
    suspend fun getHistory() {
        viewModelScope.launch {
            historyRepository.getKeywords()
                .map { resource ->
                    State.fromResource(resource)
                }
                .collect { state -> _keywords.value = state }
        }
    }

    suspend fun postHistory(keyword: String) {
        viewModelScope.launch(ioDispatcher) {
            historyRepository.insert(keyword, currMillis)
        }
    }

    suspend fun delHistory(kid: Long) {
        viewModelScope.launch(ioDispatcher) {
            historyRepository.deleteKeyword(kid)
        }
    }

    suspend fun clearHistory() {
        viewModelScope.launch(ioDispatcher) {
            historyRepository.clearKeywords()
        }
    }
}