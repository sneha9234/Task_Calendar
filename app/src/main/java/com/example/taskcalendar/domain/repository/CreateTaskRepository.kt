package com.example.taskcalendar.domain.repository

import com.example.taskcalendar.domain.model.request.TaskData
import com.example.taskcalendar.domain.model.response.Status
import retrofit2.Response

interface CreateTaskRepository {
    suspend fun createTask(taskData: TaskData): Response<Status>
}
