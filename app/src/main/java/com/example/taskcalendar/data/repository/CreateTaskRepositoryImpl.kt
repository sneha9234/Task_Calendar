package com.example.taskcalendar.data.repository

import com.example.taskcalendar.common.PrefsDataStore
import com.example.taskcalendar.data.service.HomeService
import com.example.taskcalendar.domain.model.request.StoreTaskRequest
import com.example.taskcalendar.domain.model.request.TaskData
import com.example.taskcalendar.domain.model.response.Status
import com.example.taskcalendar.domain.repository.CreateTaskRepository
import com.example.taskcalendar.util.generateRandomUserId
import kotlinx.coroutines.flow.first
import retrofit2.Response
import javax.inject.Inject

class CreateTaskRepositoryImpl @Inject constructor(
    private val homeService: HomeService,
    private val persistenceManager: PrefsDataStore
) : CreateTaskRepository {
    override suspend fun createTask(taskData: TaskData): Response<Status> {
        val userId = persistenceManager.userId.first() ?: generateRandomUserId()
        persistenceManager.storeUserId(userId)
        val result = homeService.createTask(StoreTaskRequest(userId, taskData))
        return result
    }

}