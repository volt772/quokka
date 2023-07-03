package com.kakao.quokka.ui

import androidx.lifecycle.viewModelScope
import com.apx6.chipmunk.app.ui.base.BaseViewModel
import com.kakao.domain.dto.QkdHamster
import com.kakao.domain.repository.QkdHamsterRepository
import com.kakao.quokka.di.IoDispatcher
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DashBoardViewModel @Inject constructor(
    @IoDispatcher val ioDispatcher: CoroutineDispatcher,
    private val hamsterRepository: QkdHamsterRepository
) : BaseViewModel() {

    private val _hamster: MutableSharedFlow<QkdHamster> = MutableSharedFlow()
    val hamster: SharedFlow<QkdHamster> = _hamster

    fun getHamster() {
//        viewModelScope.launch {
//            val result = hamsterRepository.getHamster()
//        }
    }

    fun getTest() {
//        println("probe :: test hamster!!")
    }
}