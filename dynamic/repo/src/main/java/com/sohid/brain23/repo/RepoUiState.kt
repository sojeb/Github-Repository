package com.sohid.brain23.repo

import com.sohid.brain23.model.Repo

data class RepoUiState(
  val repo: Repo = Repo.EMPTY,
  val isLoading: Boolean = false
) {

  companion object {

    val EMPTY = RepoUiState()
  }
}