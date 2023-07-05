package com.kakao.quokka.preference

import android.content.SharedPreferences


interface QkPreference {

    val preferences: SharedPreferences

    fun setFileUrl(key: String, value: String)

    fun getFileUrl(key: String, defaultValue: String): String?

    fun getAllFiles(): Map<String, String>

    fun getFileKey(key: String): Boolean

    fun delFileKey(key: String)

//    fun setInt(key: String, value: Int)
//
//    fun getInt(key: String, defaultValue: Int): Int?
//
//    fun setLong(key: String, value: Long)
//
//    fun getLong(key: String, defaultValue: Long): Long
//
    fun setString(key: String, value: String)
//
//    fun getString(key: String, defaultValue: String): String?
//
//    fun setBoolean(key: String, value: Boolean)
//
//    fun getBoolean(key: String, defaultValue: Boolean): Boolean
    fun setStringSet(key: String, value: Set<String>)
//    fun getStringSet(key: String, value: String)
    fun getStringSet(key: String): Set<String>

    fun clear()

    fun removePref()
}
