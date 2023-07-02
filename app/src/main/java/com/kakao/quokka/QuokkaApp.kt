package com.kakao.quokka

import android.app.Application
import android.content.Context
import com.kakao.sdk.common.KakaoSdk
import com.kako.quokka.R
import dagger.hilt.android.HiltAndroidApp


@HiltAndroidApp
class QuokkaApp : Application() {

    override fun onCreate() {
        super.onCreate()

        appContext = applicationContext

        KakaoSdk.init(this, getString(R.string.kakao_app_key))
    }
    companion object {
        lateinit var appContext: Context
            private set
    }
}