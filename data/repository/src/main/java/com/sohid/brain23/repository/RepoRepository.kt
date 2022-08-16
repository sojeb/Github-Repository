package com.sohid.brain23.repository

import com.sohid.brain23.api.RepoApi
import com.sohid.brain23.database.RepoDb
import com.sohid.brain23.model.Repo
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RepoRepository @Inject constructor(
  private val repoDb: RepoDb,
  private val repoApi: RepoApi
) {

  suspend fun getRepo(owner: String, repo: String): Repo {
    return repoApi.getRepo(owner, repo)
  }

  suspend fun saveRepository(repo: Repo) {
    repoDb.insert(repo)
  }

  suspend fun deleteRepository(repo: Repo) {
    repoDb.delete(repo)
  }

  suspend fun findAllSavedRepository(): List<Repo> {
    return repoDb.getAll()
  }
}
