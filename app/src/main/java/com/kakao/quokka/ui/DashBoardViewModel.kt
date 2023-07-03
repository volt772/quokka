package com.kakao.quokka.ui

import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.apx6.chipmunk.app.ui.base.BaseViewModel
import com.kakao.domain.dto.QkDocuments
import com.kakao.domain.dto.QkdHamster
import com.kakao.domain.repository.QkdHamsterRepository
import com.kakao.quokka.di.IoDispatcher
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DashBoardViewModel @Inject constructor(
    @IoDispatcher val ioDispatcher: CoroutineDispatcher,
    private val repository: QkdHamsterRepository
) : BaseViewModel() {

    private val _query: MutableSharedFlow<String> = MutableSharedFlow()
    val query: SharedFlow<String> = _query

    private val _hamster: MutableSharedFlow<QkdHamster> = MutableSharedFlow()
    val hamster: SharedFlow<QkdHamster> = _hamster

    suspend fun queryDocuments(query: String) {
        println("probe :: vm save : query : $query")
        viewModelScope.launch {
            println("probe :: vm save : do emit")
            _query.emit(query)
        }
    }

    fun getHamster() {
//        viewModelScope.launch {
//            val result = hamsterRepository.getHamster()
//        }
    }

    fun getTest() {
//        println("probe :: test hamster!!")
    }

//    suspend fun getDocuments(): Flow<PagingData<QkDocuments>> {
//        println("probe :: >>>>>>>>>..")
//        return repository.documents()
////            .map { pagingData ->
////                pagingData.map {
////                    mapper.mapDomainMovieToUi(domainMovie = it)
////                }
////            }
//            .cachedIn(viewModelScope)
//    }
}