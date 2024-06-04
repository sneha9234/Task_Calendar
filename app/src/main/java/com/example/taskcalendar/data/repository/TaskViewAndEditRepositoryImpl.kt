package com.example.taskcalendar.data.repository

import com.example.taskcalendar.common.PrefsDataStore
import com.example.taskcalendar.data.service.HomeService
import com.example.taskcalendar.domain.model.request.DeleteRequest
import com.example.taskcalendar.domain.model.request.TaskRequest
import com.example.taskcalendar.domain.model.response.Status
import com.example.taskcalendar.domain.model.response.Tasks
import com.example.taskcalendar.domain.repository.TaskViewAndEditRepository
import com.example.taskcalendar.util.generateRandomUserId
import kotlinx.coroutines.flow.first
import retrofit2.Response

import javax.inject.Inject

class TaskViewAndEditRepositoryImpl @Inject constructor(
    private val homeService: HomeService,
    private val persistenceManager: PrefsDataStore
): TaskViewAndEditRepository {
    override suspend fun getTasksList(): Response<Tasks> {
        val userId = persistenceManager.userId.first() ?: generateRandomUserId()
        persistenceManager.storeUserId(userId)
        val result = homeService.getTasksList(TaskRequest(userId))
        return result
    }

    override suspend fun deleteTask(taskId: Int): Response<Status> {
        val userId = persistenceManager.userId.first()
        val result = homeService.deleteTask(DeleteRequest(userId?:1234,taskId))
        return result
    }
}
