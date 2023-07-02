package com.kakao.quokka.di

import android.app.Application
import android.content.Context
import com.kakao.quokka.preference.QkPreference
import com.kakao.quokka.preference.QkPreferenceImpl
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
    abstract fun bindContext(application: Application): Context

    @Binds
    @Singleton
    abstract fun bindMpPreferences(impl: QkPreferenceImpl): QkPreference

}