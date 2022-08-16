package com.sohid.brain23.base.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sohid.brain23.base.ErrorHandler
import com.sohid.brain23.model.Repo
import com.sohid.brain23.repository.RepoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * [ViewModel] to store and manage user-related data.
 * This should be Activity-Scope, because the data is used across screens.
 */
@HiltViewModel
class UserViewModel @Inject constructor(
  private val repoRepository: RepoRepository,
  private val errorHandler: ErrorHandler
) : ViewModel() {

  private val _uiState: MutableStateFlow<UserUiState> = MutableStateFlow(UserUiState.EMPTY)
  val uiState: StateFlow<UserUiState> = _uiState

  init {
    viewModelScope.launch {
      runCatching {
        repoRepository.findAllSavedRepository()
      }.onSuccess { repos ->
        _uiState.value = _uiState.value.copy(pinnedRepos = repos)
      }.onFailure { error ->
        errorHandler.handleError(error)
      }
    }
  }

  fun saveData(repo: Repo) {
    viewModelScope.launch {
      runCatching {
        repoRepository.saveRepository(repo)
        val repositories = _uiState.value.pinnedRepos.toMutableList()
        repositories.apply { add(repo) }
      }.onSuccess { repos ->
        _uiState.value = _uiState.value.copy(pinnedRepos = repos)
      }.onFailure { error ->
        errorHandler.handleError(error)
      }
    }
  }

  fun removeHistory(repo: Repo) {
    viewModelScope.launch {
      runCatching {
        repoRepository.deleteRepository(repo)
        val repositories = _uiState.value.pinnedRepos.toMutableList()
        repositories.apply { remove(repo) }
      }.onSuccess { repos ->
        _uiState.value = _uiState.value.copy(pinnedRepos = repos)
      }.onFailure { error ->
        errorHandler.handleError(error)
      }
    }
  }

  fun isPinned(repo: Repo): Boolean {
    return _uiState.value.pinnedRepos.contains(repo)
  }
}
