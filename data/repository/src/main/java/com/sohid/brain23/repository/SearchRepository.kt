package com.sohid.brain23.repository

import com.sohid.brain23.api.SearchApi
import com.sohid.brain23.model.Repo
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SearchRepository @Inject constructor(
  private val searchApi: SearchApi
) {

  suspend fun searchHotRepos(): List<Repo> {
    return searchApi.getHotRepos()
  }


}
