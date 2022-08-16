package com.sohid.brain23.base.ui

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.fragment.app.FragmentActivity

val LocalActivity = staticCompositionLocalOf<FragmentActivity> {
  error("Activity not found")
}