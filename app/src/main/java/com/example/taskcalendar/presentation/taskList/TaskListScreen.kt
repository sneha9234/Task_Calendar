package com.example.taskcalendar.presentation.taskList

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.runtime.getValue
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.taskcalendar.R
import com.example.taskcalendar.presentation.components.TaskItem
import com.example.taskcalendar.presentation.home.HomeScreenState
import com.example.taskcalendar.presentation.home.TaskViewAndEditViewModel

@Composable
fun TaskListScreen(
    scrollState: LazyListState,
    navController: NavHostController,
    viewModel: TaskViewAndEditViewModel = hiltViewModel()
) {
    val homeScreenState by viewModel.homeScreenState

    ShowList(homeScreenState, scrollState, viewModel)

}

@Composable
private fun ShowList(
    homeScreenState: HomeScreenState,
    scrollState: LazyListState,
    viewModel: TaskViewAndEditViewModel
) {
    val tasks = homeScreenState.tasksList?.tasks
    if (tasks?.isNotEmpty() == true) {
        LazyColumn(
            modifier =
            Modifier
                .fillMaxWidth(),
            state = scrollState,
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            items(tasks.size) {
                TaskItem(
                    viewModel,
                    task = tasks[it],
                    modifier = Modifier
                        .clip(RoundedCornerShape(5.dp))
                )
                Divider(color = Color.Black)
            }
        }
    } else if (homeScreenState.tasksList?.tasks?.isEmpty() == true) {
        NoFileUI()
    }
}

@Composable
fun NoFileUI() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(R.string.no_tasks_found),
            fontSize = 24.sp
        )
    }
}
