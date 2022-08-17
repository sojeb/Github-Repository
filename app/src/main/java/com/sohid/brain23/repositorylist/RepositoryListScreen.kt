package com.sohid.brain23.repositorylist

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.sohid.brain23.base.ui.LocalActivity
import com.sohid.brain23.base.ui.component.ProgressView
import com.sohid.brain23.base.ui.component.RepoCell
import com.sohid.brain23.base.util.collectAsLifecycleAwareState
import com.sohid.brain23.base.viewmodel.NavigationViewModel
import com.sohid.brain23.base.viewmodel.SystemViewModel
import com.sohid.brain23.base.viewmodel.UserUiState
import com.sohid.brain23.base.viewmodel.UserViewModel
import com.sohid.brain23.model.Repo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

@Composable
fun RepositoryListScreen(
  repositoryViewModel: RepositoryListViewModel = hiltViewModel(),
  systemViewModel: SystemViewModel = viewModel(LocalActivity.current),
  userViewModel: UserViewModel = viewModel(LocalActivity.current),
  navigationViewModel: NavigationViewModel = viewModel(LocalActivity.current),
) {
 // private val _uiState = MutableStateFlow(RepositoryListUiState.EMPTY)
  //val uiState: StateFlow<RepositoryListUiState> = _uiState


  val uiState by repositoryViewModel.uiState.collectAsLifecycleAwareState()

   //var _uiState: MutableStateFlow<RepositoryListUiState> = MutableStateFlow(RepositoryListUiState.EMPTY)

  val context = LocalContext.current

  Scaffold(
    topBar = {
      DropdownDemo(uiState,repositoryViewModel)
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
fun DropdownDemo(  repositoryListUiState: RepositoryListUiState,   repositoryViewModel: RepositoryListViewModel) {
  var expanded by remember { mutableStateOf(false) }
  val items = listOf("Sort By Star", "Sort By Date")
  var selectedIndex by remember { mutableStateOf(0) }
  Box(modifier = Modifier.fillMaxWidth(),
    contentAlignment = Alignment.TopEnd
  ) {

    Text(items[selectedIndex], fontSize = 20.sp, modifier = Modifier.wrapContentSize().align(Alignment.TopEnd).padding(0.dp,0.dp,5.dp,0.dp).clickable(onClick = { expanded = true }))
    DropdownMenu(

      expanded = expanded,
      onDismissRequest = { expanded = false },
      offset = DpOffset(x = (-66).dp, y = (-10).dp),
      modifier = Modifier.wrapContentSize().align(Alignment.TopEnd)) {
      items.forEachIndexed { index, s ->
        DropdownMenuItem(onClick = {
          repositoryViewModel.setOrdering(items[index])
          selectedIndex = index
          expanded = false
          repositoryViewModel.fetchingRepository()

        }) {
          Text(text = s )
        }
      }
    }
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
