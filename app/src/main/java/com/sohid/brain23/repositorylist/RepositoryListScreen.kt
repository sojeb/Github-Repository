package com.sohid.brain23.repositorylist

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Divider
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.sohid.brain23.base.ui.LocalActivity
import com.sohid.brain23.base.ui.component.ProgressView
import com.sohid.brain23.base.ui.component.RepoCell
import com.sohid.brain23.base.util.collectAsLifecycleAwareState
import com.sohid.brain23.base.viewmodel.NavigationViewModel
import com.sohid.brain23.base.viewmodel.SystemViewModel
import com.sohid.brain23.base.viewmodel.UserViewModel
import com.sohid.brain23.model.Repo

@Composable
fun RepositoryListScreen(
  repositoryViewModel: RepositoryListViewModel = hiltViewModel(),
  systemViewModel: SystemViewModel = viewModel(LocalActivity.current),
  userViewModel: UserViewModel = viewModel(LocalActivity.current),
  navigationViewModel: NavigationViewModel = viewModel(LocalActivity.current),
) {

  val uiState by repositoryViewModel.uiState.collectAsLifecycleAwareState()

  val context = LocalContext.current

  Scaffold(
    topBar = {

    },
    content = { paddingValues ->
      RepositoryBody(
        modifier = Modifier.padding(paddingValues),
        repositoryListUiState = uiState,
        onRepoClicked = { repo -> navigationViewModel.openRepo(repo.owner.login, repo.name)
          if(!userViewModel.isPinned(repo)){
            userViewModel.saveData(repo)
          }
          },
        onRepoLongClicked = {
        },
      )
    }
  )

  if (uiState.isLoading) {
    ProgressView()
  }
}


@Composable
fun RepositoryBody(
  modifier: Modifier,
  repositoryListUiState: RepositoryListUiState,
  onRepoClicked: (repo: Repo) -> Unit,
  onRepoLongClicked: (repo: Repo) -> Unit,
) {
  val languages = "Android"
  Column(modifier = modifier) {
    RepoItems(
      modifier = Modifier.fillMaxWidth(),
      language = languages,
      uiState = repositoryListUiState,
      onRepoClicked = onRepoClicked,
      onRepoLongClicked = onRepoLongClicked,
    )
  }
}

@Composable
fun RepoItems(
  modifier: Modifier,
  language: String,
  uiState: RepositoryListUiState,
  onRepoClicked: (repo: Repo) -> Unit,
  onRepoLongClicked: (repo: Repo) -> Unit,
) {
  LazyColumn(modifier = modifier) {
    val repos = uiState.hotRepos[language] ?: emptyList()
    for (repo in repos) {
      item {
        RepoCell(
          modifier = Modifier.fillMaxWidth(),
          repo = repo,
          onClicked = onRepoClicked,
          onLongClicked = onRepoLongClicked,
        )
        Divider()
      }
    }
  }
}
