package com.example.taskcalendar.domain.model.response

import com.google.gson.annotations.SerializedName

data class Tasks(
    val tasks: List<Task>,
    var noOfItems: Int
)

data class  Task(
    @SerializedName("task_id")
    val taskId : Int,
    @SerializedName("task_detail")
    val taskDetail : TaskDetail
)

data class TaskDetail(
    val title : String ?= null,
    val description : String ?= null
)
