package com.kakao.quokka.ui

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.kakao.quokka.ext.statusBar
import com.kakao.quokka.ui.base.BaseActivity
import com.kako.quokka.R
import com.kako.quokka.databinding.ActivitySplashBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class SplashActivity : BaseActivity<SplashViewModel, ActivitySplashBinding>() {

    override val viewModel: SplashViewModel by viewModels()
    override fun getViewBinding(): ActivitySplashBinding = ActivitySplashBinding.inflate(layoutInflater)

    override fun onCreate(savedInstanceState: Bundle?) {

        val splashScreen = installSplashScreen()

        super.onCreate(savedInstanceState)

        initView()
        splashScreen.setKeepOnScreenCondition { true }
    }


    private fun initView() {
        this.statusBar(R.color.white)
        moveToDashBoard()
    }

    private fun moveToDashBoard() {
        val intent = Intent(this, DashBoardActivity::class.java)
        startActivity(intent)
    }

    companion object {
        const val TAG = "SplashActivity"
    }
}