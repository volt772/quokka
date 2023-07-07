package com.kakao.quokka.ext

import android.app.Activity
import android.os.SystemClock
import android.view.View
import android.view.WindowInsetsController
import com.kako.quokka.R

/**
 * Component, Visibility (single)
 * VISIBLE / GONE
 */
fun View?.visibilityExt(isVisibility: Boolean) {
    this?.visibility = if (isVisibility) View.VISIBLE else View.GONE
}

private const val MIN_CLICK_INTERVAL = 500L

/**
 * Single Click
 */
fun View.setOnSingleClickListener(onSingleClick: (View) -> Unit) {
    var lastClickTime = 0L

    setOnClickListener {
        val elapsedTime = SystemClock.elapsedRealtime() - lastClickTime
        if (elapsedTime < MIN_CLICK_INTERVAL) return@setOnClickListener
        lastClickTime = SystemClock.elapsedRealtime()

        onSingleClick(this)
    }
}

/* Status Bar*/
fun Activity?.statusBar(color: Int? = R.color.white) {
    this?.let { _activity ->
        _activity.window.apply {
            insetsController?.setSystemBarsAppearance(
                WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS,
                WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS
            )

            color?.let { _color ->
                statusBarColor = getColor(_color)
            }
        }
    }
}
