package com.example.taskcalendar.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.taskcalendar.domain.model.request.TaskData
import com.example.taskcalendar.presentation.calendar.CreateTaskViewModel
import com.example.taskcalendar.util.getMonthDays
import com.example.taskcalendar.util.getStartingDayOfMonth
import kotlinx.coroutines.launch

@Composable
fun MonthItem(
    viewModel: CreateTaskViewModel,
    month: String, year: Int,
    showLeftArrow: Boolean,
    showRightArrow: Boolean
) {
    val daysOfWeek = listOf("Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat")
    val monthDays = getMonthDays(month, year)
    val startingDayOfMonth = getStartingDayOfMonth(month, year)

    val showDialog = remember { mutableStateOf(false) }
    val selectedDate = remember { mutableStateOf(0) }

    Column(
        modifier = Modifier.padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row {
            if (showLeftArrow) {
                Icon(
                    Icons.Filled.ArrowBack,
                    contentDescription = "Previous Month",
                    tint = Color.Green
                )
            }
            Spacer(modifier = Modifier.width(25.dp))
            Text(
                text = "$month $year",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.width(25.dp))
            if (showRightArrow) {
                Icon(
                    Icons.Filled.ArrowForward,
                    contentDescription = "Next Month",
                    tint = Color.Green
                )
            }
        }
        Spacer(modifier = Modifier.height(8.dp))

        // Display days of the week
        Row(Modifier.fillMaxWidth()) {
            for (day in daysOfWeek) {
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = 4.dp)
                ) {
                    Text(
                        text = day,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Display dates of the month mapped to the correct day
        val daysInMonth = monthDays.size
        val totalDaysIncludingEmptyCells = daysInMonth + startingDayOfMonth
        val rows = (totalDaysIncludingEmptyCells + 6) / 7 // Add 6 to account for rounding up
        for (rowIndex in 0 until rows) {
            Row(Modifier.fillMaxWidth()) {
                for (i in 0 until 7) {
                    val dayIndex = rowIndex * 7 + i - startingDayOfMonth + 1
                    val date = if (dayIndex in 1..daysInMonth) dayIndex else null
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .padding(horizontal = 4.dp)
                            .clickable {
                                if (date != null) {
                                    selectedDate.value = date
                                    showDialog.value = true
                                }
                            }
                    ) {
                        date?.let {
                            Text(
                                text = it.toString(),
                                fontSize = 16.sp,
                                textAlign = TextAlign.Center,
                                modifier = Modifier.align(Alignment.Center)
                            )
                        }
                    }
                }
            }
        }
    }
    val coroutineScope = rememberCoroutineScope()

    if (showDialog.value) {
        DateInputDialog(
            onDismiss = { showDialog.value = false },
            onSubmit = { title, desc ->
                coroutineScope.launch {
                    viewModel.createTask(TaskData(title = title, description = desc))
                }
            }
        )
    }
}