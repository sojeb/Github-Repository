package com.sohid.brain23.repositorylist

import com.sohid.brain23.model.Repo

data class RepositoryListUiState(
  val hotRepos: Map<String, List<Repo>> = emptyMap(),
  val result: List<Repo> = listOf(),
  val isLoading: Boolean = false,
) {

  companion object {

    val EMPTY = RepositoryListUiState()
  }
}