package me.dannly.mirainikki.navigation

import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.compose.currentBackStackEntryAsState
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.launch
import me.dannly.mirainikki.R
import me.dannly.mirainikki.presentation.note_add.NoteAddEvent
import me.dannly.mirainikki.presentation.note_add.NoteAddViewModel
import me.dannly.mirainikki.ui.theme.LocalNavigation

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun AppBottomDrawer(content: @Composable () -> Unit) {
    val controller = LocalNavigation.current
    val destination = controller.currentBackStackEntryAsState().value?.destination
    val route = destination?.route
    val scope = rememberCoroutineScope()
    when {
        route?.startsWith(Routes.ADD_NOTE) == true -> {
            val viewModel: NoteAddViewModel =
                "${Routes.ADD_NOTE}?${Routes.ADD_NOTE_ID_ARGUMENT}={${Routes.ADD_NOTE_ID_ARGUMENT}}".toNavViewModel()
            val state = rememberBottomDrawerState(initialValue = BottomDrawerValue.Closed)
            LaunchedEffect(key1 = Unit) {
                viewModel.uiEvent.mapNotNull { it as? NoteAddEvent.UiEvent.SetDrawerValue }
                    .collect {
                        when(it.bottomDrawerValue) {
                            BottomDrawerValue.Closed -> state.close()
                            BottomDrawerValue.Open -> state.open()
                            BottomDrawerValue.Expanded -> state.expand()
                        }
                    }
            }
            BottomDrawer(drawerState = state, drawerContent = {
                ListItem(icon = {
                    Icon(
                        contentDescription = null,
                        painter = painterResource(id = R.drawable.ic_baseline_image_24)
                    )
                }, text = {
                    Text(text = stringResource(R.string.add_image))
                }, modifier = Modifier.clickable {
                    scope.launch {
                        state.close()
                    }
                    viewModel.sendUiEvent(NoteAddEvent.UiEvent.LaunchContract)
                })
            }) {
                content()
            }
        }
        else -> content()
    }
}