package com.sohid.brain23.di

import com.sohid.brain23.base.ErrorHandler
import com.sohid.brain23.repository.RepoRepository
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@EntryPoint
@InstallIn(SingletonComponent::class)
interface RepoAppModuleDependencies {

  fun provideRepoRepository(): RepoRepository

  fun provideErrorHandler(): ErrorHandler
}
