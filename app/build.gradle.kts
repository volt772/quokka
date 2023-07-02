plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-android")
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
}

android {
    namespace = "com.kako.quokka"
    compileSdk = Version.compileSdkVersion

    defaultConfig {
        applicationId = "com.kakao.quokka"
        minSdk = Version.minSdkVersion
        targetSdk = Version.targetSdkVersion
        versionCode = Version.versionCode
        versionName = Version.versionName

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        val options = this
        options.jvmTarget = "11"
    }
    buildFeatures {
        viewBinding = true
        dataBinding = true
    }
    /* ONLY Hilt / Coroutine Test*/
    packagingOptions {
        resources {
            excludes += "/META-INF/*"
        }
    }
}

dependencies {

    /* Android*/
    implementation(Android.coreKtx)
    implementation(Android.appCompat)
    implementation(Android.material)
    implementation(Android.constraintLayout)
    implementation(Android.fragmentKtx)
    implementation(Android.navigationKtx)
    implementation(Android.workRunTime)
    implementation(Android.workRunTimeKtx)

    /* LifeCycle*/
    implementation(Lifecycle.viewModel)
    implementation(Lifecycle.liveData)
    implementation(Lifecycle.runtimeKtx)

    /* Coroutine*/
    implementation(Coroutines.core)
    implementation(Coroutines.android)

    /* Hilt*/
    implementation(Hilt.dagger)
    kapt(Hilt.compiler)
    implementation(Hilt.worker)
    kapt(Hilt.workerCompiler)

    /* Paging3*/
    implementation(Paging3.ktx)
    implementation(Paging3.rxJava3)
    implementation(Paging3.roomPaging)

    /* Test*/
    androidTestImplementation(Test.jUnit)
    androidTestImplementation(Test.workTest)
    androidTestImplementation(Test.coreTest)
    androidTestImplementation(Test.coreKtx)
    androidTestImplementation(Test.espresso)
    androidTestImplementation(Test.espressoContrib)
    androidTestImplementation(Test.espressoIntents)
    androidTestImplementation(Test.jUnitExt)
    androidTestImplementation(Test.jUnitKtx)
    androidTestImplementation(Test.rules)
    androidTestImplementation(Test.runner)
    androidTestImplementation(Test.fragment)
    androidTestImplementation(Test.espressoWeb)

    debugImplementation(Test.core)
    androidTestImplementation(Test.hiltTest)
    androidTestImplementation(Test.coroutineTest)
    kaptAndroidTest(Test.daggerHilt)

    /* Dependency*/
    implementation(project(":domain"))
    implementation(project(":data"))
}