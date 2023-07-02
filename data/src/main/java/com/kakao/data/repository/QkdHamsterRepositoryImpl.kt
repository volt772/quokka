package com.kakao.data.repository

import com.kakao.data.di.IoDispatcher
import com.kakao.data.network.QkdApiService
import com.kakao.data.response.QkdResponseRefinery
import com.kakao.data.response.QkdResult
import com.kakao.domain.dto.QkdHamster
import com.kakao.domain.repository.QkdHamsterRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import okhttp3.internal.wait
import javax.inject.Inject

class QkdHamsterRepositoryImpl @Inject constructor(
    @IoDispatcher val ioDispatcher: CoroutineDispatcher,
    private val refinery: QkdResponseRefinery,
    private val api: QkdApiService
): QkdHamsterRepository {

    override suspend fun getHamster() {

        try {
            CoroutineScope(ioDispatcher).launch {
                val rImage = async {
                    refinery.response(
                        api.getSearchImage(query = "hamster", page = 1, size = 10, sort = "recency")
                    )
//                    when (r1) {
//                        is QkdResult.Success -> {
//                            r1.data.body
//                        }
//
//                        is QkdResult.Error -> {
//                        }
//                        }
                    }
                val rVclip = async {
                    refinery.response(
                        api.getSearchVClip(query = "hamster", page = 1, size = 10, sort = "recency")
                    )
                }


                val resImage = rImage.await()
                val resVclip = rVclip.await()

                val r1 = when (resImage) {
                    is QkdResult.Success -> {
                        resImage.data.body
                    }

                    is QkdResult.Error -> {
                        resImage.exception
                    }
                }

                val r2 = when (resVclip) {
                    is QkdResult.Success -> {
                        resVclip.data.body
                    }

                    is QkdResult.Error -> {
                        resVclip.exception
                    }
                }

                println("probe :: rImage : ${r1}")
                println("probe :: rVclip : ${r2}")
            }
        } catch (e: Exception) {
            println("probe :: err : ${e}")
        }
    }
}