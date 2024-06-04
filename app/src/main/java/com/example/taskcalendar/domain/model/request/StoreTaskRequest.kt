package com.example.taskcalendar.domain.model.request

import com.google.gson.annotations.SerializedName

data class StoreTaskRequest(
    @SerializedName("user_id")
    val userId : Int,
    val task: TaskData
)

data class TaskData(
    @SerializedName("title")
    val title : String,
    @SerializedName("description")
    val description: String
)