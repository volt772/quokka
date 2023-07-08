package com.kakao.quokka.ui.cabinet

import androidx.lifecycle.viewModelScope
import com.kakao.quokka.ext.splitKey
import com.kakao.quokka.model.CabinetModel
import com.kakao.quokka.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


/**
 * CabinetViewModel
 */
@HiltViewModel
class CabinetViewModel @Inject constructor(
) : BaseViewModel() {

    /* History Keywords*/
    private val _favorites: MutableSharedFlow<List<CabinetModel>> = MutableSharedFlow()
    val favorites: SharedFlow<List<CabinetModel>> = _favorites

    /**
     * Switch Favorite Flag
     * @desc like to dislike
     */
    suspend fun setFavorites(favors: Set<String>) {
        viewModelScope.launch {
            val list = mutableListOf<CabinetModel>().also {_list ->
                favors.forEach { fv ->
                    val keySet = fv.splitKey()
                    val url = keySet.first
                    val regDate = keySet.second
                    _list.add(CabinetModel(url = url, regDate = regDate))
                }
            }

            list.sortBy { it.regDate }
            _favorites.emit(list)
        }
    }
}