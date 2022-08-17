package com.sohid.brain23.repositorylist

import com.sohid.brain23.model.Repo

data class RepositoryListUiState(
 /* "Sort By Star", "Sort By Date"*/
  val hotRepos: Map<String, List<Repo>> = emptyMap(),
  val result: List<Repo> = listOf(),
  val isLoading: Boolean = false,
  var searchOrder: String = "Sort By Star"
) {

  companion object {

    val EMPTY = RepositoryListUiState()
  }
}