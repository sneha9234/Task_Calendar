package com.example.taskcalendar.util

sealed class Screen(val route: String) {

    object HomeScreen : Screen(route = "home_screen")
    object CalendarScreen : Screen(route = "calendar_screen")
    object TaskListScreen : Screen(route = "task_list_screen")

    fun withArgs(vararg args: String?): String {
        return buildString {
            append(route)
            args.forEach { arg ->
                append("/$arg")
            }
        }
    }
}