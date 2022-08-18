package com.sohid.brain23.repositorylist

import android.content.SharedPreferences
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
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
  private val errorHandler: ErrorHandler,
  private val sharedPreferences: SharedPreferences
) : ViewModel() {
  val sortOrderkey: String= "sort_order";
  val sortOrderDefaultValue: String= "By Star Count";
  var TAG : String="Repository List "
  private val _uiState = MutableStateFlow(RepositoryListUiState.EMPTY)
  val uiState: StateFlow<RepositoryListUiState> = _uiState
  var oldOrder: String="";

  private val _sortOrder= mutableStateOf(oldOrder)
  val sortOrder: State<String> = _sortOrder


  init {
      _sortOrder.value = getOrdering()
  }
  fun isSameOrder (order:String): Boolean{
    if(oldOrder== order){
      return true;

    }else{
      oldOrder=order;
      return false;

    }
  }
  fun setOrdering(order: String){
    sharedPreferences.edit().putString(sortOrderkey,order).apply()
    _sortOrder.value = order
  }
  fun getOrdering(): String {
  return  sharedPreferences.getString(sortOrderkey,sortOrderDefaultValue).toString()
  }
   fun fetchingRepository(order : String){
     if(isSameOrder(order)) return;

    viewModelScope.launch {
      runCatching {
        _uiState.value = _uiState.value.copy(isLoading = true)
        mutableMapOf<String, List<Repo>>().also { map ->
          coroutineScope {
            async { map["Android"] = if(order=="By Star Count") searchRepository.searchHotRepos() else searchRepository.searchLatestRepos() }.await()
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
