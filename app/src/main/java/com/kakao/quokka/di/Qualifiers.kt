package com.kakao.quokka.di

import javax.inject.Qualifier

/**
 * @Related
 * NetworkModule
 */
@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class ChipmunkBaseUrl

/**
 * @Related
 * Dispatchers
 */
@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class IoDispatcher

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class DefaultDispatcher

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class MainDispatcher