package com.kakao.data.di

import javax.inject.Qualifier

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

/**
 * @Related
 * Database
 */
@Retention(AnnotationRetention.RUNTIME)
@Qualifier
annotation class ApplicationScope