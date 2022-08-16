package com.sohid.brain23.ui

import android.os.Bundle
import android.view.View
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.sohid.brain23.R
import com.sohid.brain23.base.ui.LocalActivity
import com.sohid.brain23.base.ui.theme.AppTheme
import com.sohid.brain23.base.viewmodel.SystemViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainTabFragment : Fragment(R.layout.fragment_main_tab) {

  private val systemViewModel: SystemViewModel by activityViewModels()

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    view.findViewById<ComposeView>(R.id.compose_view).setContent {
      AppTheme() {
        CompositionLocalProvider(
          LocalActivity provides requireActivity()
        ) {
          AppTheme {
            MainContent()
          }
        }
      }
    }
  }
}
