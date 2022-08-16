package com.sohid.brain23.base.di

import android.app.Application
import androidx.recyclerview.widget.RecyclerView
import com.sohid.brain23.base.AppErrorHandler
import com.sohid.brain23.base.ErrorHandler
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

  @Singleton
  @Provides
  fun provideErrorHandler(context: Application): ErrorHandler {
    return AppErrorHandler(context)
  }

  @Singleton
  @Provides
  fun provideRecycledViewPool(): RecyclerView.RecycledViewPool {
    return RecyclerView.RecycledViewPool()
  }
}
