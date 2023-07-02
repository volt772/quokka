package com.kakao.data.repository

import com.kakao.data.di.IoDispatcher
import com.kakao.data.network.QkdApiService
import com.kakao.domain.repository.QkdHamsterRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

class QkdHamsterRepositoryImpl @Inject constructor(
    @IoDispatcher val ioDispatcher: CoroutineDispatcher,
    private val api: QkdApiService
): QkdHamsterRepository {

    override suspend fun getHamster() {

        CoroutineScope(ioDispatcher).launch {
            val rImage = async {
                api.getSearchImage(query = "hamster", page = 1, size = 10, sort = "recency")
            }
            val rVclip = async {
                api.getSearchVClip(query = "hamster", page = 1, size = 10, sort = "recency")
            }

            val resImage = rImage.await()
            val resVclip = rVclip.await()

            println("probe :: rImage : ${resImage.body()}")
            println("probe :: rVclip : ${resVclip.body()}")
        }
    }
}