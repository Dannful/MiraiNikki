package me.dannly.mirainikki

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import dagger.hilt.android.AndroidEntryPoint
import me.dannly.mirainikki.navigation.AppBottomDrawer
import me.dannly.mirainikki.presentation.note_list.components.AddNoteFab
import me.dannly.mirainikki.ui.theme.LocalNavigation
import me.dannly.mirainikki.ui.theme.MiraiNikkiTheme
import me.dannly.mirainikki.navigation.Routes
import me.dannly.mirainikki.navigation.appTopBar
import me.dannly.mirainikki.presentation.note_add.NoteAddScreen
import me.dannly.mirainikki.presentation.note_list.NoteListScreen
import me.dannly.mirainikki.ui.theme.LocalScaffoldState

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MiraiNikkiTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    val navController = rememberNavController()
                    val scaffoldState = rememberScaffoldState()
                    CompositionLocalProvider(
                        LocalNavigation provides navController,
                        LocalScaffoldState provides scaffoldState
                    ) {
                        AppBottomDrawer {
                            Scaffold(scaffoldState = scaffoldState, floatingActionButton = {
                                if (navController.currentBackStackEntryAsState().value?.destination?.route?.equals(
                                        Routes.NOTES_LIST
                                    ) == true
                                ) {
                                    AddNoteFab {
                                        navController.navigate(Routes.ADD_NOTE)
                                    }
                                }
                            }, topBar = appTopBar) {
                                NavHost(
                                    navController = navController,
                                    startDestination = Routes.NOTES_LIST
                                ) {
                                    composable(route = Routes.NOTES_LIST) {
                                        NoteListScreen {
                                            navController.navigate("${Routes.ADD_NOTE}?${Routes.ADD_NOTE_ID_ARGUMENT}=$it")
                                        }
                                    }
                                    composable(route = "${Routes.ADD_NOTE}?${Routes.ADD_NOTE_ID_ARGUMENT}={${Routes.ADD_NOTE_ID_ARGUMENT}}",
                                        arguments = listOf(
                                            navArgument(name = Routes.ADD_NOTE_ID_ARGUMENT) {
                                                type = NavType.IntType
                                                defaultValue = -1
                                            }
                                        )) {
                                        NoteAddScreen {

                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}