plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("kotlin-android")
    id("kotlin-kapt")
    id("kotlin-parcelize")
}

android {
    namespace = "com.kako.domain"
    compileSdk = Version.compileSdkVersion

    defaultConfig {
        minSdk = Version.minSdkVersion
        targetSdk = Version.targetSdkVersion

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
}

dependencies {

    /* Hilt*/
    implementation(Hilt.dagger)
    kapt(Hilt.compiler)

    /* Retrofit*/
    implementation(Retrofit.core)
    implementation(Retrofit.converterGson)
    implementation(Retrofit.adapterRxJava)
    implementation(Retrofit.adapterRxJava2)
    implementation(Retrofit.loggingInterceptor)

    /* Paging3*/
    implementation(Paging3.ktx)
    implementation(Paging3.rxJava3)
    implementation(Paging3.roomPaging)

    implementation(Android.coreKtx)
    androidTestImplementation(Test.hiltTest)
    androidTestImplementation(Test.coroutineTest)
    kaptAndroidTest(Test.daggerHilt)

    androidTestImplementation(Test.jUnit)
    androidTestImplementation(Test.jUnitExt)
    androidTestImplementation(Test.jUnitKtx)
    androidTestImplementation(Test.rules)
    androidTestImplementation(Test.runner)
}