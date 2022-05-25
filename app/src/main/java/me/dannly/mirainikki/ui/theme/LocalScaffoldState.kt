package me.dannly.mirainikki.ui.theme

import androidx.compose.material.ScaffoldState
import androidx.compose.runtime.compositionLocalOf

val LocalScaffoldState = compositionLocalOf<ScaffoldState> { throw NullPointerException() }