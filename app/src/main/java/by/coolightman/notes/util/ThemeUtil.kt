package by.coolightman.notes.util

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import by.coolightman.notes.ui.theme.NightAccent

@Composable
fun isDarkMode(): Boolean = MaterialTheme.colors.secondary == NightAccent