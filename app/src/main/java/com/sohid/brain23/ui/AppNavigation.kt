package com.sohid.brain23.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.sohid.brain23.history.HistoryScreen
import com.sohid.brain23.repositorylist.RepositoryListScreen

sealed class Screen(
  val route: String
) {
  object RepositoryList : Screen("repository")
  object History : Screen("history")
}

@Composable
fun AppNavigation(
  modifier: Modifier,
  navController: NavHostController,
) {
  NavHost(
    modifier = modifier,
    navController = navController,
    startDestination = Screen.RepositoryList.route,
  ) {
    composable(Screen.RepositoryList.route) {
      RepositoryListScreen()
    }

    composable(Screen.History.route) {
      HistoryScreen()
    }
  }
}