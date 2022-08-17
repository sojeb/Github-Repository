package com.sohid.brain23.repository

import android.util.Log
import com.sohid.brain23.api.SearchApi
import com.sohid.brain23.model.Repo
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SearchRepository @Inject constructor(

  private val searchApi: SearchApi
) {
  var TAG: String="API method"

  suspend fun searchHotRepos(): List<Repo> {
    Log.i(TAG, "method called for api hotRepo")
    return searchApi.getHotRepos()
  }

  suspend fun searchLatestRepos(): List<Repo> {
    Log.i(TAG, "method called for api latestRepos")
    return searchApi.getLatestRepo()
  }
}
