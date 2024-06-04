package com.example.taskcalendar.domain.model.request

import com.google.gson.annotations.SerializedName

data class DeleteRequest(
    @SerializedName("user_id")
    val userId: Int,
    @SerializedName("task_id")
    val taskId: Int
)