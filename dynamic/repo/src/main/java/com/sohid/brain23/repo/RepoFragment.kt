package com.sohid.brain23.repo

import android.os.Bundle
import android.view.View
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.sohid.brain23.base.ui.theme.AppTheme
import com.sohid.brain23.base.viewmodel.NavigationViewModel
import com.sohid.brain23.base.viewmodel.SystemViewModel
import com.sohid.brain23.di.RepoAppModuleDependencies
import dagger.hilt.android.EntryPointAccessors
import io.noties.markwon.Markwon
import javax.inject.Inject

class RepoFragment : Fragment(R.layout.fragment_repo) {

  @Inject
  lateinit var repoViewModelProviderFactory: RepoViewModelProviderFactory

  @Inject
  lateinit var markwon: Markwon

  private val navigationViewModel: NavigationViewModel by activityViewModels()
  private val systemViewModel: SystemViewModel by activityViewModels()
  private val repoViewModel: RepoViewModel by viewModels { repoViewModelProviderFactory }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    DaggerRepoComponent.builder()
      .fragment(this)
      .repoModule(RepoModule())
      .appDependencies(
        EntryPointAccessors.fromApplication(
          requireContext().applicationContext,
          RepoAppModuleDependencies::class.java
        )
      )
      .build()
      .inject(this)
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    view.findViewById<ComposeView>(R.id.compose_view).setContent {
      AppTheme() {
        CompositionLocalProvider(
          LocalMarkwon provides markwon,
        ) {
          RepoScreen(repoViewModel, navigationViewModel)
        }
      }
    }
  }
}
