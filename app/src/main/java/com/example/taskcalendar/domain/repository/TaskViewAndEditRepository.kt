package com.example.taskcalendar.domain.repository

import com.example.taskcalendar.domain.model.response.Status
import com.example.taskcalendar.domain.model.response.Tasks
import retrofit2.Response

interface TaskViewAndEditRepository {
    suspend fun getTasksList(): Response<Tasks>
    suspend fun deleteTask(taskId: Int): Response<Status>
}
