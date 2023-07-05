package com.kakao.quokka.ui.dashboard

import com.kakao.domain.repository.QkdHamsterRepository
import com.kakao.quokka.di.IoDispatcher
import com.kakao.quokka.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

@HiltViewModel
class DashBoardViewModel @Inject constructor(
) : BaseViewModel() {
}