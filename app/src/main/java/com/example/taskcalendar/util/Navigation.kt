package com.example.taskcalendar.util

import android.annotation.SuppressLint
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.taskcalendar.presentation.calendar.CalendarScreen
import com.example.taskcalendar.presentation.home.HomeScreen
import com.example.taskcalendar.presentation.taskList.TaskListScreen

@Composable
fun NavigationGraph(
    navController: NavHostController,
    startDestination: String,
    scrollState: LazyListState
) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(
            route = Screen.HomeScreen.route
        ) {
            HomeScreen(navController = navController)
        }
        composable(route = Screen.CalendarScreen.route + "/{month}/{year}",
            arguments = listOf(
                navArgument("month") {
                    type = NavType.StringType
                    nullable = false
                },
                navArgument("year") {
                    type = NavType.StringType
                    nullable = false
                }
            )
        ) { entry ->
            CalendarScreen(
                month = entry.arguments?.getString("month"),
                year = entry.arguments?.getString("year"),
                navController = navController
            )
        }
        composable(
            route = Screen.TaskListScreen.route
        ) {
            TaskListScreen(scrollState = scrollState, navController = navController)
        }
    }

}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun SetupNavigation(startDestination: String) {

    val navController = rememberNavController()


    val scrollState = rememberLazyListState()

    NavigationGraph(
        navController = navController,
        startDestination = startDestination,
        scrollState = scrollState
    )

}
