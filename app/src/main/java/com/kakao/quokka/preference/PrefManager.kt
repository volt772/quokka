package com.kakao.quokka.preference

import android.content.SharedPreferences


interface PrefManager {

    val preferences: SharedPreferences

    fun setStringSet(key: String, value: Set<String>)

    fun getStringSet(key: String): Set<String>

    fun addDocUrl(url: String)

    fun removeDocUrl(url: String)

}
