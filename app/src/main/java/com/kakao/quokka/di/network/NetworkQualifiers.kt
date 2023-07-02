package com.kakao.quokka.di.network

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

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class ProgressInterceptor

/**
 * @Related
 * HttpClients
 */
@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class GeneralHttpClient

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class ProgressHttpClient

/**
 * @Related
 * Network API Service Router
 */
@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class QuokkaService