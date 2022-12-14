package com.sohid.brain23.repo

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.AppBarDefaults
import androidx.compose.material.ContentAlpha
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AltRoute
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import coil.transform.RoundedCornersTransformation
import com.sohid.brain23.base.ui.component.BackArrowIconButton
import com.sohid.brain23.base.ui.component.ProgressView
import com.sohid.brain23.base.ui.component.TopBar
import com.sohid.brain23.base.util.collectAsLifecycleAwareState
import com.sohid.brain23.base.viewmodel.NavigationViewModel
import com.sohid.brain23.database.LocalDateTimeConverter
import com.sohid.brain23.model.Repo

@Composable
fun RepoScreen(
  repoViewModel: RepoViewModel,
  navigationViewModel: NavigationViewModel
) {
  val uiState by repoViewModel.uiState.collectAsLifecycleAwareState()

  val scrollState = rememberScrollState()

  Scaffold(
    topBar = {
      RepoTopBar(
        uiState = uiState,
        scrollState = scrollState,
        onBackPressed = { navigationViewModel.popBackStack() },
      )
    },
    content = { paddingValues ->
      RepoBody(
        modifier = Modifier.padding(paddingValues),
        uiState = uiState,
        scrollState = scrollState)

    }
  )

  if (uiState.isLoading) {
    ProgressView()
  }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun RepoTopBar(
  uiState: RepoUiState,
  scrollState: ScrollState,
  onBackPressed: () -> Unit,
) {
  TopBar(
    title = {
      AnimatedVisibility(
        visible = 200 < scrollState.value,
        enter = fadeIn() + slideInVertically(initialOffsetY = { it / 2 }),
        exit = fadeOut() + slideOutVertically(targetOffsetY = { it / 2 })
      ) {
        Column(
          verticalArrangement = Arrangement.spacedBy(2.dp)
        ) {
          Text(
            text = uiState.repo.name,
            style = MaterialTheme.typography.h6,
            fontWeight = FontWeight.Bold,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
          )
          Text(
            text = uiState.repo.owner.login,
            style = MaterialTheme.typography.subtitle2,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
          )
        }
      }
    },
    navigationIcon = { BackArrowIconButton(onPressed = onBackPressed) },
    elevation = if (scrollState.value == 0) 0.dp else AppBarDefaults.TopAppBarElevation,
  )
}

@Composable
fun RepoBody(
  modifier: Modifier,
  uiState: RepoUiState,
  scrollState: ScrollState,
) {
  Column(modifier = modifier.verticalScroll(scrollState)) {
    RepoOverview(
      modifier = Modifier.fillMaxWidth(),
      repo = uiState.repo

    )
  }
}

@Composable
fun RepoOverview(
  modifier: Modifier,
  repo: Repo,
) {
  Column(modifier = modifier) {
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 8.dp)
        .clickable {  },
      verticalAlignment = Alignment.CenterVertically,
    ) {
      if (repo.owner.avatarUrl.isNotEmpty()) {
        Image(
          modifier = Modifier
            .padding(start = 8.dp)
            .padding(vertical = 8.dp)
            .size(200.dp),
          painter = rememberImagePainter(
            data = repo.owner.avatarUrl,
            builder = {
              transformations(RoundedCornersTransformation(16.0f))
            }
          ),
          contentDescription = null
        )
      }
      Text(
        text = repo.owner.login,
        modifier = Modifier.padding(start = 8.dp, end = 16.dp),
        style = MaterialTheme.typography.h6,
          fontWeight = FontWeight.Bold,
      )
    }
    Text(
      text = repo.name,
      modifier = Modifier.padding(horizontal = 16.dp),
      style = MaterialTheme.typography.h5,
      fontWeight = FontWeight.Bold,
    )
    Text(
      text = repo.description,
      modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
      style = MaterialTheme.typography.body1
    )
    Row(
      modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
      verticalAlignment = Alignment.CenterVertically,
    ) {
      Icon(
        modifier = Modifier.size(size = 18.dp),
        imageVector = Icons.Default.Star,
        contentDescription = null,
        tint = MaterialTheme.colors.onSurface.copy(alpha = ContentAlpha.medium)
      )
      Text(
        text = "${repo.starsCount} stars",
        modifier = Modifier.padding(start = 8.dp, end = 16.dp)
      )
      Icon(
        modifier = Modifier.size(size = 18.dp),
        imageVector = Icons.Default.AltRoute,
        contentDescription = null,
        tint = MaterialTheme.colors.onSurface.copy(alpha = ContentAlpha.medium)
      )
      Text(text = "${repo.forksCount} forks", modifier = Modifier.padding(start = 8.dp))
    }
    Row(
      modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
      verticalAlignment = Alignment.CenterVertically,
    ) {

      Icon(
        modifier = Modifier.size(size = 18.dp),
        imageVector = Icons.Default.CalendarToday,
        contentDescription = null,
        tint = MaterialTheme.colors.onSurface.copy(alpha = ContentAlpha.medium)
      )
      if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
        Text(text = "Updated At: ${LocalDateTimeConverter().getFormatedDate(repo.updatedAt)}", modifier = Modifier.padding(start = 8.dp))

      }else{
        Text(text = "Local Date is not Supported bellow android 8", modifier = Modifier.padding(start = 8.dp))

      }
        }
  }
}


