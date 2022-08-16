package com.sohid.brain23.api

import com.sohid.brain23.model.Repo

interface SearchApi {

  suspend fun getHotRepos(): List<Repo>


}
