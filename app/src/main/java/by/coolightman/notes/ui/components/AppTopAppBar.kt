package by.coolightman.notes.ui.components

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun AppTopAppBar(
    modifier: Modifier = Modifier,
    title: @Composable (() -> Unit)? = null,
    navigationIcon: @Composable (() -> Unit)? = null,
    actions: @Composable (RowScope.() -> Unit)? = null
) {

    TopAppBar(
        title = { title?.let { title() } },
        navigationIcon = { navigationIcon?.let { navigationIcon() } },
        actions = { actions?.let { actions() } },
        modifier = modifier
    )
}