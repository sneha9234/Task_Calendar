package com.example.taskcalendar.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.taskcalendar.domain.model.response.Task
import com.example.taskcalendar.presentation.home.TaskViewAndEditViewModel
import kotlinx.coroutines.launch

@Composable
fun TaskItem(
    viewModel: TaskViewAndEditViewModel,
    task: Task,
    modifier: Modifier
) {
    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp)
    ) {
        Row(
            modifier = modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.padding(vertical = 15.dp, horizontal = 10.dp)) {
                Text(
                    text = task.taskDetail.title ?: "",
                    color = Color.Blue,
                )
                Text(
                    text = task.taskDetail.description ?: "",
                    color = Color.Black,
                )
            }
            IconButton(onClick = {
                coroutineScope.launch {
                    viewModel.deleteTask(task.taskId)
                }
            }) {
                Icon(Icons.Filled.Delete, contentDescription = "delete task")
            }
        }
    }
    Spacer(modifier = Modifier.height(2.dp))
}