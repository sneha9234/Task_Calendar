package com.example.taskcalendar.presentation.home

import com.example.taskcalendar.domain.model.response.Tasks


data class HomeScreenState(
    val tasksList: Tasks? = null
)