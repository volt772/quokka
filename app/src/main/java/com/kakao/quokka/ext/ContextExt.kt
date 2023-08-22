package com.kakao.quokka.ext

import android.content.Context
import android.widget.Toast
import androidx.annotation.StringRes


fun Context.showToast(message: String, isLong: Boolean = false) {
    Toast.makeText(this, message, if (isLong) Toast.LENGTH_LONG else Toast.LENGTH_SHORT).show()
}

fun Context.showToast(@StringRes resourceId: Int, isLong: Boolean = false) {
    showToast(getString(resourceId), isLong)
}

