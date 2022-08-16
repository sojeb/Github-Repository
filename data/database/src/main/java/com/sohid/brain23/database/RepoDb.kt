package com.sohid.brain23.database

import com.sohid.brain23.model.Repo

interface RepoDb {

  suspend fun getAll(): List<Repo>

  suspend fun insert(repo: Repo)

  suspend fun delete(repo: Repo)

  suspend fun deleteAll()
}
