package com.example.taskcalendar.presentation.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.Button
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ExposedDropdownMenuBox
import androidx.compose.material.ExposedDropdownMenuDefaults
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.taskcalendar.R
import com.example.taskcalendar.ui.DarkGreen
import com.example.taskcalendar.util.Screen
import java.util.Calendar

@Composable
fun HomeScreen(navController: NavHostController) {
    var selectedMonth by remember {
        mutableStateOf(
            getMonthName(
                Calendar.getInstance().get(Calendar.MONTH)
            )
        )
    }
    var selectedYear by remember {
        mutableStateOf(
            Calendar.getInstance().get(Calendar.YEAR).toString())
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        MonthDropDown(selectedMonth = selectedMonth, onMonthSelected = { month ->
            selectedMonth = month
        })
        Spacer(modifier = Modifier.height(20.dp))
        YearDropDown(selectedYear = selectedYear,
            onYearSelected = { year ->
                selectedYear = year
            })
        Spacer(modifier = Modifier.height(20.dp))
        Button(
            modifier = Modifier
                .width(200.dp)
                .padding(5.dp),
            onClick = {
                navController.navigate(
                    Screen.CalendarScreen.withArgs(
                        selectedMonth,
                        selectedYear
                    )
                )
            }
        ) {
            Text(
                text = stringResource(R.string.continue_),
                fontSize = 16.sp
            )
        }
        Spacer(modifier = Modifier.height(50.dp))
        Text(text = AnnotatedString(stringResource(R.string.or)))
        Spacer(modifier = Modifier.height(10.dp))
        ClickableText(
            text = AnnotatedString(stringResource(R.string.view_tasks)),
            onClick = {
                navController.navigate(Screen.TaskListScreen.route)
            },
            style = TextStyle(
                color = DarkGreen,
                fontSize = 20.sp,
            )
        )
    }
}


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MonthDropDown(selectedMonth: String, onMonthSelected: (String) -> Unit) {

    val months = listOf(
        "January",
        "February",
        "March",
        "April",
        "May",
        "June",
        "July",
        "August",
        "September",
        "October",
        "November",
        "December"
    )

    var expanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(expanded = expanded, onExpandedChange = {
        expanded = !expanded
    }) {
        TextField(
            readOnly = true,
            value = selectedMonth,
            onValueChange = { },
            label = { Text("Select Month") },
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(
                    expanded = expanded
                )
            },
            colors = ExposedDropdownMenuDefaults.textFieldColors()
        )
        ExposedDropdownMenu(expanded = expanded, onDismissRequest = {
            expanded = false
        }) {
            months.forEach { selectionOption ->
                DropdownMenuItem(onClick = {
                    onMonthSelected(selectionOption)
                    expanded = false
                }) {
                    Text(text = selectionOption)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun YearDropDown(
    selectedYear: String, onYearSelected: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(expanded = expanded, onExpandedChange = {
        expanded = !expanded
    }) {
        TextField(
            readOnly = true,
            value = selectedYear,
            onValueChange = { },
            label = { Text("Select Year") },
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(
                    expanded = expanded
                )
            },
            colors = ExposedDropdownMenuDefaults.textFieldColors()
        )
        ExposedDropdownMenu(expanded = expanded, onDismissRequest = {
            expanded = false
        }) {
            for (i in getYearListUsingCalendar()) {
                DropdownMenuItem(onClick = {
                    onYearSelected(i.toString())
                    expanded = false
                }) {
                    Text(text = i.toString())
                }
            }
        }
    }
}

fun getYearListUsingCalendar(): List<Int> {
    val currentYear = Calendar.getInstance().get(Calendar.YEAR)
    val years = mutableListOf<Int>()
    for (year in currentYear - 50..currentYear + 50) {
        years.add(year)
    }
    return years
}

fun getMonthName(month: Int): String {
    return when (month) {
        Calendar.JANUARY -> "January"
        Calendar.FEBRUARY -> "February"
        Calendar.MARCH -> "March"
        Calendar.APRIL -> "April"
        Calendar.MAY -> "May"
        Calendar.JUNE -> "June"
        Calendar.JULY -> "July"
        Calendar.AUGUST -> "August"
        Calendar.SEPTEMBER -> "September"
        Calendar.OCTOBER -> "October"
        Calendar.NOVEMBER -> "November"
        Calendar.DECEMBER -> "December"
        else -> "Invalid month"
    }
}