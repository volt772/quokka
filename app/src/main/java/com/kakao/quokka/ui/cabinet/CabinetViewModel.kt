package com.kakao.quokka.ui.cabinet

import androidx.lifecycle.viewModelScope
import com.apx6.chipmunk.app.ui.base.BaseViewModel
import com.kakao.quokka.di.IoDispatcher
import com.kakao.quokka.ext.splitUrlKey
import com.kakao.quokka.model.CabinetModel
import com.kakao.quokka.preference.PrefManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CabinetViewModel @Inject constructor(
    @IoDispatcher val ioDispatcher: CoroutineDispatcher,
    private val prefManager: PrefManager,
//    private val repository: QkdHamsterRepository,
//    private val mapper: DocumentsMapper
) : BaseViewModel() {

    private val _favorites: MutableSharedFlow<List<CabinetModel>> = MutableSharedFlow()
    val favorites: SharedFlow<List<CabinetModel>> = _favorites

    suspend fun setFavorites(favors: Set<String>) {
        viewModelScope.launch {
            val list = mutableListOf<CabinetModel>().also {_list ->
                favors.forEach { fv ->
                    val keySet = fv.splitUrlKey()
                    val url = keySet.first
                    val regDate = keySet.second
                    _list.add(CabinetModel(url = url, regDate = regDate))
                }
            }

            list.sortBy { it.regDate }
            _favorites.emit(list)
        }
    }


//    private val _favorites: MutableSharedFlow<List<FavoritesModel>> = MutableSharedFlow()
//    val favorites: SharedFlow<List<FavoritesModel>> = _favorites

    suspend fun getAllFavorites() {

//        val files = qkPreference.getAllFiles()
//
//        val favors = mutableListOf<FavoritesModel>().also { _list ->
//            files.forEach { _f ->
//                _list.add(FavoritesModel(key = _f.key, url = _f.value))
//            }
//        }
//
//        _favorites.emit(favors)

    }

}