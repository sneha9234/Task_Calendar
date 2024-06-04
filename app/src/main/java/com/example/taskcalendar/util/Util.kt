package com.example.taskcalendar.util

fun generateRandomUserId(): Int {
    // Generate a random userId
    return (0..99999).random()
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