package com.sohid.brain23.di

import android.content.Context
import android.content.SharedPreferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class SharedPreferencesModule {

    @Singleton
    @Provides
    fun provideSharedPreference(@ApplicationContext context: Context): SharedPreferences {
        return context.getSharedPreferences(context.packageName + "_preferences", Context.MODE_PRIVATE)
    }
}

@Suppress("unused")
abstract class NullableLongApplicationPref(private val sharedPreferences: SharedPreferences) : ApplicationPref() {
    var value: Long?
        get() = if (sharedPreferences.contains(prefName)) sharedPreferences.getLong(prefName, -1) else null
        set(value) = value?.let { sharedPreferences.edit().putLong(prefName, value).apply() }
            ?: run { sharedPreferences.edit().remove(prefName).apply() }
}

@Suppress("unused")
abstract class NonNullLongApplicationPref(private val sharedPreferences: SharedPreferences, private val defaultValue: Long) : ApplicationPref() {
    var value: Long
        get() = sharedPreferences.getLong(prefName, defaultValue)
        set(value) = sharedPreferences.edit().putLong(prefName, value).apply()
}

@Suppress("unused")
abstract class NullableIntApplicationPref(private val sharedPreferences: SharedPreferences) : ApplicationPref() {
    var value: Int?
        get() = if (sharedPreferences.contains(prefName)) sharedPreferences.getInt(prefName, -1) else null
        set(value) = value?.let { sharedPreferences.edit().putInt(prefName, value).apply() }
            ?: run { sharedPreferences.edit().remove(prefName).apply() }
}

@Suppress("unused")
abstract class NonNullIntApplicationPref(private val sharedPreferences: SharedPreferences, private val defaultValue: Int) : ApplicationPref() {
    var value: Int
        get() = sharedPreferences.getInt(prefName, defaultValue)
        set(value) = sharedPreferences.edit().putInt(prefName, value).apply()
}

@Suppress("unused")
abstract class NullableBooleanApplicationPref(private val sharedPreferences: SharedPreferences) : ApplicationPref() {
    var value: Boolean?
        get() = if (sharedPreferences.contains(prefName)) sharedPreferences.getBoolean(prefName, false) else null
        set(value) = value?.let { sharedPreferences.edit().putBoolean(prefName, value).apply() }
            ?: run { sharedPreferences.edit().remove(prefName).apply() }
}

@Suppress("unused")
abstract class NonNullBooleanApplicationPref(private val sharedPreferences: SharedPreferences, private val defaultValue: Boolean) : ApplicationPref() {
    var value: Boolean
        get() = sharedPreferences.getBoolean(prefName, defaultValue)
        set(value) = sharedPreferences.edit().putBoolean(prefName, value).apply()
}

@Suppress("unused")
abstract class NullableStringApplicationPref(private val sharedPreferences: SharedPreferences) : ApplicationPref() {
    var value: String?
        get() = sharedPreferences.getString(prefName, null)
        set(value) = sharedPreferences.edit().putString(prefName, value).apply()
}

@Suppress("unused")
abstract class NonNullStringApplicationPref(private val sharedPreferences: SharedPreferences, private val defaultValue: String) : ApplicationPref() {
    var value: String
        get() = sharedPreferences.getString(prefName, defaultValue)!!
        set(value) = sharedPreferences.edit().putString(prefName, value).apply()
}

abstract class ApplicationPref() {
    protected val prefName = this.javaClass.simpleName
}