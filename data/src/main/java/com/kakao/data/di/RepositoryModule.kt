package com.kakao.data.di

import com.kakao.data.repository.QkdHamsterRepositoryImpl
import com.kakao.domain.repository.QkdHamsterRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * RepositoryModule
 */

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindHamsterRepository(impl: QkdHamsterRepositoryImpl): QkdHamsterRepository
}