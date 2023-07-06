package com.kakao.quokka.preference

import android.content.SharedPreferences


interface PrefManager {

    val preferences: SharedPreferences

    fun setStringSet(key: String, value: Set<String>)

    fun getStringSet(key: String): Set<String>

    fun removeString(key: String, value: String)

    fun addStringSet(key: String, value: String)

    fun removeStringSet(key: String, value: String)

    fun clearKey(key: String)

}
