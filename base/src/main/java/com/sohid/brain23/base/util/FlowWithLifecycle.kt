package com.sohid.brain23.base.util

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

//In an Android app, Kotlin flows are typically collected from the UI layer
//to display data updates on the screen. However, you want to collect these
//flows making sure you’re not doing more work than necessary, wasting resources
//(both CPU and memory) or leaking data when   the view goes to the background.
@Composable
fun <T> rememberFlowWithLifecycle(
  flow: Flow<T>,
  lifecycle: Lifecycle = LocalLifecycleOwner.current.lifecycle,
  minActiveState: Lifecycle.State = Lifecycle.State.STARTED
): Flow<T> = remember(flow, lifecycle) {
  flow.flowWithLifecycle(
    lifecycle = lifecycle,
    minActiveState = minActiveState
  )
}

@Composable
fun <T> StateFlow<T>.collectAsLifecycleAwareState(
  lifecycle: Lifecycle = LocalLifecycleOwner.current.lifecycle,
  minActiveState: Lifecycle.State = Lifecycle.State.STARTED
): State<T> {
  return rememberFlowWithLifecycle(
    flow = this,
    lifecycle = lifecycle,
    minActiveState = minActiveState
  ).collectAsState(initial = value)
}
