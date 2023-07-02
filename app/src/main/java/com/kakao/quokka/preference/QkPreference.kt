package com.kakao.quokka.preference


interface QkPreference {

    fun setInt(key: String, value: Int)

    fun getInt(key: String, defaultValue: Int): Int?

    fun setLong(key: String, value: Long)

    fun getLong(key: String, defaultValue: Long): Long

    fun setString(key: String, value: String)

    fun getString(key: String, defaultValue: String): String?

    fun setBoolean(key: String, value: Boolean)

    fun getBoolean(key: String, defaultValue: Boolean): Boolean

    fun clear()

    fun removePref()
}
