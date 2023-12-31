object Version {
    const val compileSdkVersion = 33
    const val minSdkVersion = 31
    const val targetSdkVersion = 33
    const val versionCode = 10000
    const val versionName = "1.0.0"
}

object Dependencies {
    const val gradle = "com.android.tools.build:gradle:7.0.4"
    const val kotlinPlugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:1.8.21"
    const val hiltPlugin = "com.google.dagger:hilt-android-gradle-plugin:2.45"
}

object Android {
    const val coreKtx = "androidx.core:core-ktx:1.10.1"
    const val appCompat = "androidx.appcompat:appcompat:1.6.1"
    const val material = "com.google.android.material:material:1.9.0"
    const val constraintLayout = "androidx.constraintlayout:constraintlayout:2.1.4"
    const val liveDataKtx = "androidx.lifecycle:lifecycle-livedata-ktx:2.6.1"
    const val viewModelKtx = "androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.1"
    const val fragmentKtx = "androidx.navigation:navigation-fragment-ktx:2.6.0"
    const val navigationKtx = "androidx.navigation:navigation-ui-ktx:2.6.0"
    const val workRunTime = "androidx.work:work-runtime:2.8.0"
    const val workRunTimeKtx = "androidx.work:work-runtime-ktx:2.8.0"
    const val splashScreen = "androidx.core:core-splashscreen:1.0.1"
    const val swipeRefreshLayout = "androidx.swiperefreshlayout:swiperefreshlayout:1.1.0"
}

object Lifecycle {
    const val viewModel = "androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.1"
    const val liveData = "androidx.lifecycle:lifecycle-livedata-ktx:2.6.1"
    const val runtimeKtx = "androidx.lifecycle:lifecycle-runtime-ktx:2.6.1"
}

object Coroutines {
    const val core = "org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.4"
    const val android = "org.jetbrains.kotlinx:kotlinx-coroutines-android:1.6.4"
}

object Retrofit {
    const val retrofit = "com.squareup.retrofit2:retrofit:2.9.0"
    const val core = "com.squareup.retrofit2:retrofit:2.9.0"
    const val converterGson = "com.squareup.retrofit2:converter-gson:2.9.0"
    const val adapterRxJava = "com.squareup.retrofit2:adapter-rxjava:2.9.0"
    const val adapterRxJava2 = "com.squareup.retrofit2:adapter-rxjava2:2.9.0"
    const val loggingInterceptor = "com.squareup.okhttp3:logging-interceptor:4.9.0"
}

object Hilt {
    const val dagger = "com.google.dagger:hilt-android:2.45"
    const val compiler = "com.google.dagger:hilt-android-compiler:2.45"
    const val worker = "androidx.hilt:hilt-work:1.0.0"
    const val workerCompiler = "androidx.hilt:hilt-compiler:1.0.0"
}

object Paging3 {
    const val ktx = "androidx.paging:paging-runtime-ktx:3.1.1"
    const val rxJava3 = "androidx.paging:paging-rxjava3:3.1.1"
    const val roomPaging = "androidx.room:room-paging:2.5.0"
}

object PrefManager {
    const val preferenceKtx = "androidx.preference:preference-ktx:1.2.0"
}

object Glide {
    const val core = "com.github.bumptech.glide:glide:4.15.1"
}

object Kakao {
    const val sdkAll = "com.kakao.sdk:v2-all:2.14.0"
}

object Joda {
    const val core = "net.danlew:android.joda:2.12.5"
}

object Test {
    const val jUnit = "junit:junit:4.13.2"
    const val workTest = "android.arch.work:work-testing:1.0.1"
    const val coreTest = "androidx.arch.core:core-testing:2.2.0"
    const val coreKtx = "androidx.test:core-ktx:1.5.0"
    const val espresso = "androidx.test.espresso:espresso-core:3.5.0"
    const val espressoContrib = "androidx.test.espresso:espresso-contrib:3.5.1"
    const val espressoIntents = "androidx.test.espresso:espresso-intents:3.5.1"
    const val jUnitExt = "androidx.test.ext:junit:1.1.5"
    const val jUnitKtx = "androidx.test.ext:junit-ktx:1.1.5"
    const val rules = "androidx.test:rules:1.5.0"
    const val runner = "androidx.test:runner:1.5.2"
    const val fragment = "androidx.fragment:fragment-testing:1.5.7"
    const val espressoWeb = "androidx.test.espresso:espresso-web:3.5.1"

    const val core = "androidx.test:core:1.5.0"
    const val hiltTest = "com.google.dagger:hilt-android-testing:2.45"
    const val coroutineTest = "org.jetbrains.kotlinx:kotlinx-coroutines-test:1.6.4"
    const val daggerHilt = "com.google.dagger:hilt-android-compiler:2.45"
}