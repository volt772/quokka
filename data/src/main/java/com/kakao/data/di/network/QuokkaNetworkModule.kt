package com.kakao.data.di.network

import com.kakao.data.network.QkdApiService
import com.kakao.domain.constants.QkdConstants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Converter
import retrofit2.Retrofit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object QuokkaNetworkModule {

    @Singleton
    @Provides
    @QuokkaBaseUrl
    fun provideQuokkaBaseUrl() = QkdConstants.Uri.BASE

    /**
     * @Desc Retrofit Clients
     */
    @Provides
    @Singleton
    @QuokkaService
    fun provideAccountRetrofitClient(
        @QuokkaBaseUrl baseUrl: String,
        @GeneralHttpClient okHttpClient: OkHttpClient,
        converterFactory: Converter.Factory
    ): Retrofit {
        return retrofitBuilder(baseUrl, converterFactory, okHttpClient)
    }

    /**
     * @Desc API Services
     */
    @Singleton
    @Provides
    fun provideQuokkaApiService(
        @QuokkaService retrofit: Retrofit
    ): QkdApiService = retrofit.create(QkdApiService::class.java)


    /**
     * @Desc 일반 Retrofit Builder
     */
    private fun retrofitBuilder(
        baseUrl: String,
        converterFactory: Converter.Factory,
        okHttpClient: OkHttpClient
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(converterFactory)
            .client(okHttpClient)
            .build()
    }

//    /**
//     * @Desc 첨부파일 전용 Retrofit Builder
//     */
//    private fun retrofitAttachBuilder(
//        baseUrl: String,
//        okHttpClient: OkHttpClient
//    ): Retrofit {
//        val gson = GsonBuilder().setLenient().create()
//        return Retrofit.Builder()
//            .baseUrl(baseUrl)
//            .addConverterFactory(GsonConverterFactory.create(gson))
//            .client(okHttpClient)
//            .build()
//    }
}