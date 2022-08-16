package com.sohid.brain23.api

import com.sohid.brain23.api.response.ListResponse
import com.sohid.brain23.api.response.RepositoryResponse
import com.sohid.brain23.model.Repo
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Query

class SearchApiClient(retrofit: Retrofit) : SearchApi {

  interface Service {
   // https://api.github.com/search/repositories?q=android&sort=stars&order=dsc&page=1&per_page=1
    @GET("search/repositories")
    suspend fun getHotRepos(
      @Query("q") query: String="Android",
      @Query("sort") sort: String = "stars",
     @Query("order") order: String = "desc",
     @Query("page") page: Int = 1,
    @Query("per_page") per_page: Int = 50
    ): ListResponse<RepositoryResponse>

  }

  private val service = retrofit.create(Service::class.java)

  override suspend fun getHotRepos(
  ): List<Repo> {
    return withContext(IO) {
      service.getHotRepos( )
        .items
        ?.map { response -> response.toModel() } ?: emptyList()
    }
  }



}
