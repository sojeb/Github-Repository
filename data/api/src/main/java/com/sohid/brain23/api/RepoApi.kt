package com.sohid.brain23.api

import com.sohid.brain23.model.Repo

interface RepoApi {

  suspend fun getRepo(owner: String, repo: String): Repo

}
