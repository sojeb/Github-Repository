package com.sohid.brain23.base.ui.theme

import androidx.compose.material.Typography
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import com.sohid.brain23.base.R

private val RobotoMono = FontFamily(
  Font(R.font.roboto_mono_regular, FontWeight.Normal),
  Font(R.font.roboto_mono_medium, FontWeight.Medium),
  Font(R.font.roboto_mono_bold, FontWeight.Bold)
)

val ThemeTypography = Typography(
  defaultFontFamily = RobotoMono
)
