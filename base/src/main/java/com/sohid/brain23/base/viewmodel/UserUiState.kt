package com.sohid.brain23.base.viewmodel

import com.sohid.brain23.model.Repo

data class UserUiState(
  val pinnedRepos: List<Repo> = emptyList(),
) {

  companion object {

    val EMPTY = UserUiState()
  }
}