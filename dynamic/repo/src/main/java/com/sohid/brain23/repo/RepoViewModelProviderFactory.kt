package com.sohid.brain23.repo

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sohid.brain23.base.ErrorHandler
import com.sohid.brain23.repository.RepoRepository
import javax.inject.Inject

class RepoViewModelProviderFactory @Inject constructor(
  private val fragment: Fragment,
  private val repoRepository: RepoRepository,
  private val errorHandler: ErrorHandler
) : ViewModelProvider.NewInstanceFactory() {

  override fun <T : ViewModel> create(modelClass: Class<T>): T {
    val args = RepoFragmentArgs.fromBundle(fragment.requireArguments())
    return RepoViewModel(args.ownerName, args.repoName, repoRepository, errorHandler) as T
  }
}
