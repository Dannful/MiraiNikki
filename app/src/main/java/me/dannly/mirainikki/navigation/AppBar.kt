package me.dannly.mirainikki.navigation

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.navigation.compose.currentBackStackEntryAsState
import me.dannly.mirainikki.R
import me.dannly.mirainikki.presentation.note_add.components.noteAddActions
import me.dannly.mirainikki.ui.theme.LocalNavigation

val appTopBar: @Composable () -> Unit
    @Composable
    get() {
        val navHostController = LocalNavigation.current
        var title: String? = stringResource(id = R.string.app_name)
        var icon: (@Composable () -> Unit)? = null
        var actions: (@Composable RowScope.() -> Unit)? = null
        val route = navHostController.currentBackStackEntryAsState().value?.destination?.route
        when {
            route?.startsWith(Routes.ADD_NOTE) == true -> {
                title = stringResource(id = R.string.add_note)
                icon = {
                    IconButton(
                        onClick = {
                            navHostController.navigate(Routes.NOTES_LIST)
                        }
                    ) {
                        Icon(
                            contentDescription = stringResource(R.string.back),
                            imageVector = Icons.Default.ArrowBack
                        )
                    }
                }
                actions = noteAddActions
            }
            route?.equals(Routes.NOTES_LIST) == true -> {
                title = null
            }
            else -> Unit
        }
        return {
            if (actions != null && title != null) {
                TopAppBar(navigationIcon = icon, title = {
                    Text(text = title)
                }, actions = actions)
            } else if(title != null) {
                TopAppBar(navigationIcon = icon, title = {
                    Text(text = title)
                })
            }
        }
    }