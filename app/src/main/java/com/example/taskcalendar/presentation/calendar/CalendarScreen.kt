package com.example.taskcalendar.presentation.calendar

import android.widget.Toast
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import com.google.accompanist.pager.*
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.taskcalendar.presentation.components.MonthItem
import com.example.taskcalendar.util.getNextMonth
import com.example.taskcalendar.util.getPreviousMonth

@OptIn(ExperimentalPagerApi::class)
@Composable
fun CalendarScreen(
    month: String?, year: String?, navController: NavHostController,
    viewModel: CreateTaskViewModel = hiltViewModel()
) {
    val pagerState = rememberPagerState()
    val (previousMonth, previousYear) = getPreviousMonth(month, year!!.toInt())
    val (nextMonth, nextYear) = getNextMonth(month, year.toInt())
    val months = listOf(previousMonth, month, nextMonth)
    val years = listOf(previousYear, year.toInt(), nextYear)

    val taskCreatedState by viewModel.taskcreated

    if (taskCreatedState) {
        val context = LocalContext.current
        Toast.makeText(context, "Task Created", Toast.LENGTH_SHORT).show()
    }

    HorizontalPager(
        state = pagerState,
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(0.dp),
        verticalAlignment = Alignment.CenterVertically,
        key = null,
        content = { page ->
            months[page]?.let {
                MonthItem(
                    viewModel,
                    month = it,
                    year = years[page],
                    showLeftArrow = page > 0,
                    showRightArrow = page < 2
                )
            }
        },
        count = 3
    )

    LaunchedEffect(pagerState) {
        pagerState.scrollToPage(1)
    }
}

@Preview
@Composable
fun PreviewCalendarScreen() {
    CalendarScreen("January", "2022", rememberNavController())
}