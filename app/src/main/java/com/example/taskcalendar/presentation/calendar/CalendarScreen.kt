package com.example.taskcalendar.presentation.calendar

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.compose.material.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.rememberNavController
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import com.google.accompanist.pager.*
import androidx.compose.runtime.remember
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.input.ImeAction
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.taskcalendar.domain.model.request.TaskData
import kotlinx.coroutines.launch

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


fun getStartingDayOfMonth(month: String, year: Int): Int {
    val monthIndex = listOf(
        "January", "February", "March", "April", "May", "June",
        "July", "August", "September", "October", "November", "December"
    ).indexOf(month)
    var y = year
    var m = monthIndex + 1
    if (m < 3) {
        y--
        m += 12
    }
    val k = y % 100
    val j = y / 100
    val dayOfWeek = (1 + ((26 * (m + 1)) / 10) + k + (k / 4) + ((5 * j) / 4) + ((j / 4) * 5)) % 7
    return (dayOfWeek + 5) % 7 // Adjust the result to match the indexing of daysOfWeek
}

@Preview
@Composable
fun PreviewCalendarScreen() {
    CalendarScreen("January", "2022", rememberNavController())
}

fun getPreviousMonth(month: String?, year: Int): Pair<String, Int> {
    return when (month) {
        "January" -> Pair("December", year - 1)
        else -> Pair(getPreviousMonthName(month ?: ""), year)
    }
}

fun getNextMonth(month: String?, year: Int): Pair<String, Int> {
    return when (month) {
        "December" -> Pair("January", year + 1)
        else -> Pair(getNextMonthName(month ?: ""), year)
    }
}

fun getPreviousMonthName(month: String): String {
    val months = listOf(
        "January", "February", "March", "April", "May", "June",
        "July", "August", "September", "October", "November", "December"
    )
    val index = months.indexOf(month)
    return if (index == 0) months[11] else months[index - 1]
}

fun getNextMonthName(month: String): String {
    val months = listOf(
        "January", "February", "March", "April", "May", "June",
        "July", "August", "September", "October", "November", "December"
    )
    val index = months.indexOf(month)
    return if (index == 11) months[0] else months[index + 1]
}

fun getMonthDays(month: String, year: Int): List<Int> {
    val daysInMonth = when (month) {
        "January", "March", "May", "July", "August", "October", "December" -> 31
        "April", "June", "September", "November" -> 30
        "February" -> if (year % 4 == 0 && (year % 100 != 0 || year % 400 == 0)) 29 else 28
        else -> 0
    }
    return (1..daysInMonth).toList()
}

@Composable
fun DateInputDialog(
    onDismiss: () -> Unit,
    onSubmit: (String, String) -> Unit
) {
    val titleText = remember { mutableStateOf("") }
    val descText = remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = { onDismiss() },
        title = {
        },
        text = {
            Column {
                Text("Enter Title")
                Spacer(modifier = Modifier.height(5.dp))
                TextField(
                    value = titleText.value,
                    onValueChange = { newText -> titleText.value = newText },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RectangleShape,
                    colors = TextFieldDefaults.textFieldColors(
                        backgroundColor = Color(0xFFE1E1E1),
                        cursorColor = Color.White,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                    ),
                    keyboardOptions = KeyboardOptions.Default.copy(
                        imeAction = ImeAction.Next
                    ),
                    singleLine = true
                )
                Spacer(modifier = Modifier.height(10.dp))
                Text("Enter Description")
                Spacer(modifier = Modifier.height(5.dp))
                TextField(
                    value = descText.value,
                    onValueChange = { newText -> descText.value = newText },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RectangleShape,
                    colors = TextFieldDefaults.textFieldColors(
                        backgroundColor = Color(0xFFE1E1E1),
                        cursorColor = Color.White,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                    ),
                    keyboardOptions = KeyboardOptions.Default.copy(
                        imeAction = ImeAction.Next
                    ),
                    singleLine = true
                )
            }

        },
        confirmButton = {
            Button(
                onClick = {
                    onSubmit(titleText.value, descText.value)
                    onDismiss()
                }
            ) {
                Text("Submit")
            }
        }
    )
}
