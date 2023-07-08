package com.kakao.quokka.ui.base

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableSharedFlow


/**
 * BaseViewModel
 */
abstract class BaseViewModel : ViewModel() {

    companion object {
        const val TAG = "BaseViewModel"
    }
}