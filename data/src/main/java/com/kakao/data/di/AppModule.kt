package com.kakao.data.di

import com.kakao.data.response.QkdResponseRefinery
import com.kakao.data.response.QkdResponseRefineryImpl
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
    abstract fun bindQkdResponseRefinery(impl: QkdResponseRefineryImpl): QkdResponseRefinery

}