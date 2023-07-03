package com.kakao.data.di

import androidx.paging.ExperimentalPagingApi
import com.kakao.data.datasource.search.SearchRemoteDataSource
import com.kakao.data.datasource.search.SearchRemoteDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
abstract class PagingModule {

    @Binds
    @Singleton
    @ExperimentalPagingApi
    abstract fun bindSearchRemoteDataSource(impl: SearchRemoteDataSourceImpl): SearchRemoteDataSource

}
