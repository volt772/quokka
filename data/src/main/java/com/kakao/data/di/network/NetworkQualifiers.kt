package com.kakao.data.di.network

import javax.inject.Qualifier

/**
 * @Related
 * NetworkModule
 */
@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class QuokkaBaseUrl

/**
 * @Related
 * Interceptors
 */
@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class GeneralInterceptor

/**
 * @Related
 * HttpClients
 */
@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class GeneralHttpClient

/**
 * @Related
 * Network API Service Router
 */
@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class QuokkaService