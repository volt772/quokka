package com.kakao.quokka.ext

import android.app.Activity
import android.content.Context
import android.view.inputmethod.InputMethodManager
import android.widget.EditText


fun Activity.hideKeyboard() {
    val view = this.currentFocus
    view?.let {
        inputMethodManager.hideSoftInputFromWindow(it.windowToken, 0)
    }
}

fun Activity.showKeyboard(view: EditText?) {
    inputMethodManager.showSoftInput(view, InputMethodManager.RESULT_UNCHANGED_SHOWN)
}

val Context.inputMethodManager: InputMethodManager
    get() = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
