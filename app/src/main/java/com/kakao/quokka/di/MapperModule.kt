package com.kakao.quokka.di

import com.kakao.quokka.mapper.DocumentsMapper
import com.kakao.quokka.mapper.DocumentsMapperImpl
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
    abstract fun bindDocumentsMapper(impl: DocumentsMapperImpl): DocumentsMapper

}
