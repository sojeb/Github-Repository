package com.sohid.brain23.repositorylist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sohid.brain23.base.ErrorHandler
import com.sohid.brain23.model.Repo
import com.sohid.brain23.repository.SearchRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RepositoryListViewModel @Inject constructor(
  private val searchRepository: SearchRepository,
  private val errorHandler: ErrorHandler
) : ViewModel() {

  private val _uiState = MutableStateFlow(RepositoryListUiState.EMPTY)
  val uiState: StateFlow<RepositoryListUiState> = _uiState

  init {
    viewModelScope.launch {
      runCatching {
        _uiState.value = _uiState.value.copy(isLoading = true)
        mutableMapOf<String, List<Repo>>().also { map ->
          coroutineScope {
            async { map["Android"] = searchRepository.searchHotRepos() }.await()
          }

        }
      }.onSuccess { hotRepos ->
        _uiState.value = _uiState.value.copy(hotRepos = hotRepos, isLoading = false)
      }.onFailure { error ->
        _uiState.value = _uiState.value.copy(isLoading = false)
        errorHandler.handleError(error)
      }
    }
  }
}
