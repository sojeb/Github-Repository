package com.sohid.brain23.repositorylist

import android.util.Log
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
  var TAG : String="Repository List "

  init {
    fetchingRepository()
  }
  fun setOrdering(order: String){
    Log.i(TAG, "method called for search order name1: "+order)
    _uiState.value = _uiState.value.copy(searchOrder = order)

    Log.i(TAG, "method called for search order name2: "+_uiState.value.searchOrder)
  }
   fun fetchingRepository(){
    viewModelScope.launch {
      runCatching {
        Log.i(TAG, "method called for search order name: "+_uiState.value.searchOrder)
        _uiState.value = _uiState.value.copy(isLoading = true)
        mutableMapOf<String, List<Repo>>().also { map ->
          coroutineScope {
            async { map["Android"] = if(_uiState.value.searchOrder=="Sort By Star") searchRepository.searchHotRepos() else searchRepository.searchLatestRepos() }.await()
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
