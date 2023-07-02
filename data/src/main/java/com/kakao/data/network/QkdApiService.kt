package com.kakao.data.network

import com.kakao.data.network.ApiServiceUris.URL_SEARCH_IMG
import com.kakao.data.network.ApiServiceUris.URL_SEARCH_VCLIP
import com.kakao.domain.constants.QkdNetworkKeyTags
import com.kakao.domain.dto.QkdDocuments
import com.kakao.domain.dto.QkdHamster
import com.kakao.domain.response.ResponseListDto
import retrofit2.Response
import retrofit2.http.*


interface QkdApiService {

    @GET(URL_SEARCH_IMG)
    suspend fun getSearchImage(
        @Query(QkdNetworkKeyTags.QueryParam.QUERY) query: String,
        @Query(QkdNetworkKeyTags.QueryParam.PAGE) page: Int?= 10,
        @Query(QkdNetworkKeyTags.QueryParam.SIZE) size: Int,
        @Query(QkdNetworkKeyTags.QueryParam.SORT) sort: String?= "recency",
//    ): Response<QkdHamster>
    ): Response<ResponseListDto<QkdDocuments>>

    @GET(URL_SEARCH_VCLIP)
    suspend fun getSearchVClip(
        @Query(QkdNetworkKeyTags.QueryParam.QUERY) query: String,
        @Query(QkdNetworkKeyTags.QueryParam.PAGE) page: Int?= 10,
        @Query(QkdNetworkKeyTags.QueryParam.SIZE) size: Int,
        @Query(QkdNetworkKeyTags.QueryParam.SORT) sort: String?= "recency",
//    ): Response<QkdHamster>
    ): Response<ResponseListDto<QkdDocuments>>
}
