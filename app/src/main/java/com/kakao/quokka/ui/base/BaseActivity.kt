package com.kakao.quokka.ui.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.viewbinding.ViewBinding


/**
 * BaseActivity
 */
abstract class BaseActivity<VM : ViewModel, VB : ViewBinding> : AppCompatActivity() {

    protected abstract val vm: VM

    protected lateinit var binding: VB

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = getViewBinding()
        setContentView(binding.root)
    }

    abstract fun getViewBinding(): VB
}