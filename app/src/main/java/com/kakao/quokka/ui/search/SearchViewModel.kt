package com.kakao.quokka.ui.search

import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.insertSeparators
import androidx.paging.map
import com.kakao.domain.repository.DocumentsRepository
import com.kakao.quokka.constants.QkConstants
import com.kakao.quokka.ext.splitKey
import com.kakao.quokka.mapper.DocumentsMapper
import com.kakao.quokka.model.DocumentModel
import com.kakao.quokka.model.HistoryModel
import com.kakao.quokka.preference.PrefManager
import com.kakao.quokka.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject


/**
 * SearchViewModel
 */
@HiltViewModel
class SearchViewModel @Inject constructor(
    private val prefManager: PrefManager,
    private val repository: DocumentsRepository,
    private val mapper: DocumentsMapper
) : BaseViewModel() {

    /* Search Keyword*/
    private val _query: MutableSharedFlow<String> = MutableSharedFlow()
    val query: SharedFlow<String> = _query

    /* History Keywords*/
    private val _history: MutableSharedFlow<List<HistoryModel>> = MutableSharedFlow()
    val history: SharedFlow<List<HistoryModel>> = _history

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

    /**
     * History List
     * @desc emit history model -> HistoryModel()
     */
    suspend fun getHistories() {
        viewModelScope.launch {
            val histories = prefManager.getStringSet(QkConstants.Pref.HISTORY_KEY)

            val historyModels = mutableListOf<HistoryModel>().also { _list ->
                histories.forEach { f ->
                    val keySet = f.splitKey()
                    val keyword = keySet.first
                    val regDate = keySet.second

                    _list.add(HistoryModel(keyword, regDate))
                }
            }

            historyModels.sortByDescending { it.regDate }
            _history.emit(historyModels)
        }
    }
}