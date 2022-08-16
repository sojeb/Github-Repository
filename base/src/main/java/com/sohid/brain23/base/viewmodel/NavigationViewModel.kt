package com.sohid.brain23.base.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sohid.brain23.base.model.Direction
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * [ViewModel] to manage directions, because feature module can not reference to a direction.
 */
@HiltViewModel
class NavigationViewModel @Inject constructor() : ViewModel() {

  private val mutableDirection: MutableSharedFlow<Direction> = MutableSharedFlow()
  val direction: SharedFlow<Direction> = mutableDirection

  fun openRepo(owner: String, repo: String) {
    viewModelScope.launch {
      mutableDirection.emit(Direction.Repo(owner, repo))
    }
  }


  fun popBackStack() {
    viewModelScope.launch {
      mutableDirection.emit(Direction.Pop)
    }
  }
}
