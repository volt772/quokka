package com.kakao.quokka.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.apx6.chipmunk.app.ui.base.BaseViewModel
import com.kakao.domain.dto.QkDocuments
import com.kakao.domain.dto.QkdHamster
import com.kakao.domain.repository.QkdHamsterRepository
import com.kakao.quokka.di.IoDispatcher
import com.kakao.quokka.mapper.DocumentsMapper
import com.kakao.quokka.model.DocumentDto
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

    suspend fun getDocuments(query: String): Flow<PagingData<DocumentDto>> {
        return repository.documents(query)
            .map { pagingData ->
                pagingData.map {
                    mapper.mapDocumentToUi(it)
                }
            }
            .cachedIn(viewModelScope)
    }

}
