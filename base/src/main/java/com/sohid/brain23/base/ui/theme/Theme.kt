package com.sohid.brain23.base.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable

@Composable
fun AppTheme(
  darkTheme: Boolean = isSystemInDarkTheme(),
  content: @Composable () -> Unit
) {
  MaterialTheme(
    colors = if (darkTheme) DarkThemeColors else LightThemeColors,
    typography = ThemeTypography,
    content = content
  )
}
