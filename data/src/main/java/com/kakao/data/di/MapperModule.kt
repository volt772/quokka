package com.kakao.data.di

import com.kakao.domain.mapper.QkdDocumentsMapper
import com.kakao.data.mapper.QkdDocumentsMapperImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * MapperModule
 */

@Module
@InstallIn(SingletonComponent::class)
abstract class MapperModule {

    @Binds
    @Singleton
    abstract fun bindDocumentsMapper(impl: QkdDocumentsMapperImpl): QkdDocumentsMapper

}
