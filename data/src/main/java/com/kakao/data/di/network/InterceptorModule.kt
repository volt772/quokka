package com.kakao.data.di.network

import com.kakao.domain.constants.QkdNetworkKeyTags.HEADER.AUTHORIZATION
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.logging.HttpLoggingInterceptor
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object InterceptorModule {

    @Provides
    @Singleton
    fun provideLoggingInterceptor() = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

    @Provides
    @Singleton
    @GeneralInterceptor
    fun provideInterceptor(): Interceptor {
        return Interceptor { chain ->
            val builder = chain.request().newBuilder()
                .header(AUTHORIZATION, "KakaoAK dbb8d3097da36f5c3fcd34a5e56e055b")
            chain.proceed(builder.build())
        }
    }
}
