package com.kakao.quokka.ui.base

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow

abstract class BaseViewModel : ViewModel() {
    private val _errorToast by lazy { MutableSharedFlow<String?>() }
    val errorToast: SharedFlow<String?> by lazy { _errorToast }

    companion object {
        const val TAG = "BaseViewModel"
    }
}