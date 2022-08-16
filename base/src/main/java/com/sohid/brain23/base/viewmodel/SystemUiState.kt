package com.sohid.brain23.base.viewmodel

data class SystemUiState(
  val isNightMode: Boolean = false,
) {

  companion object {

    val EMPTY = SystemUiState()
  }
}