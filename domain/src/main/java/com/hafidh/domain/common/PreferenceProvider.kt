package com.hafidh.domain.common

interface PreferenceProvider {
    fun getString(key: String, defaultValue: String = ""): String
    fun putString(key: String, value: String = "")
    fun getInt(key: String, defaultValue: Int = 0): Int
    fun putInt(key: String, value: Int = 0)
    fun getFloat(key: String, defaultValue: Float = 0.0f): Float
    fun putFloat(key: String, value: Float = 0.0f)
    fun getBoolean(key: String, defaultValue: Boolean = false): Boolean
    fun putBoolean(key: String, value: Boolean = false)
    fun clearSpecificKey(key: String): Boolean
    fun clearAll()

}