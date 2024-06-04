package com.example.taskcalendar.domain.model.request

import com.google.gson.annotations.SerializedName

data class TaskRequest(
    @SerializedName("user_id")
    val userId: Int
)