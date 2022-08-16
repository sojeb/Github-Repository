package com.sohid.brain23.api

import com.sohid.brain23.api.response.RepositoryResponse
import com.sohid.brain23.model.Repo
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Path

class RepoApiClient(retrofit: Retrofit) : RepoApi {

  interface Service {

    @GET("repos/{owner}/{repo}")
    suspend fun getRepo(
      @Path("owner") owner: String,
      @Path("repo") repo: String
    ): RepositoryResponse


  }

  private val service = retrofit.create(Service::class.java)

  override suspend fun getRepo(owner: String, repo: String): Repo {
    return withContext(IO) {
      service.getRepo(owner, repo).toModel()
    }
  }


}
