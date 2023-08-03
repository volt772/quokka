package com.kakao.data.di

import com.kakao.data.datasource.operators.RemoteKeyOperator
import com.kakao.data.datasource.operators.RemoteKeyOperatorImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * PagingModule
 */

@Module
@InstallIn(SingletonComponent::class)
abstract class PagingModule {

    @Binds
    @Singleton
    abstract fun bindRemoteKeyOperator(impl: RemoteKeyOperatorImpl): RemoteKeyOperator

}
