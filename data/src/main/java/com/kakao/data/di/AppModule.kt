package com.kakao.data.di

import androidx.paging.ExperimentalPagingApi
import com.kakao.data.datasource.search.SearchRemoteDataSource
import com.kakao.data.datasource.search.SearchRemoteDataSourceImpl
import com.kakao.data.mapper.DocumentsMapperImpl
import com.kakao.data.repository.DocumentsRepositoryImpl
import com.kakao.data.repository.HistoryRepositoryImpl
import com.kakao.domain.mapper.DocumentsMapper
import com.kakao.domain.repository.DocumentsRepository
import com.kakao.domain.repository.HistoryRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
abstract class AppModule {

    @Binds
    @Singleton
    abstract fun bindDocumentsMapper(impl: DocumentsMapperImpl): DocumentsMapper

    @Binds
    @Singleton
    @ExperimentalPagingApi
    abstract fun bindSearchRemoteDataSource(impl: SearchRemoteDataSourceImpl): SearchRemoteDataSource

    @Binds
    @Singleton
    abstract fun bindDocumentsRepository(impl: DocumentsRepositoryImpl): DocumentsRepository

    @Binds
    @Singleton
    abstract fun bindHistoryRepository(impl: HistoryRepositoryImpl): HistoryRepository

}