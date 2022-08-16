package com.sohid.brain23.api.di

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.sohid.brain23.api.RepoApi
import com.sohid.brain23.api.RepoApiClient
import com.sohid.brain23.api.SearchApi
import com.sohid.brain23.api.SearchApiClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Converter
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

  @ExperimentalSerializationApi
  @Singleton
  @Provides
  fun provideConverterFactory(): Converter.Factory {
    return Json {
      isLenient = true
      ignoreUnknownKeys = true
    }.asConverterFactory("application/json".toMediaType())
  }

  @Singleton
  @Provides
  fun provideRetrofit(okHttpClient: OkHttpClient, converterFactory: Converter.Factory): Retrofit {
    return Retrofit.Builder()
      .client(okHttpClient)
      .baseUrl("https://api.github.com/")
      .addConverterFactory(converterFactory)
      .build()
  }

  @Singleton
  @Provides
  fun provideRepoApi(retrofit: Retrofit): RepoApi {
    return RepoApiClient(retrofit)
  }


  @Singleton
  @Provides
  fun provideSearchApi(retrofit: Retrofit): SearchApi {
    return SearchApiClient(retrofit)
  }
}
