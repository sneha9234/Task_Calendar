package com.example.taskcalendar.data.service

import com.example.taskcalendar.domain.model.request.DeleteRequest
import com.example.taskcalendar.domain.model.request.StoreTaskRequest
import com.example.taskcalendar.domain.model.request.TaskRequest
import com.example.taskcalendar.domain.model.response.Status
import com.example.taskcalendar.domain.model.response.Tasks
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface HomeService {
    @POST("getCalendarTaskList")
    suspend fun getTasksList(
    @Body tasksRequest: TaskRequest
    ) : Response<Tasks>

    @POST("deleteCalendarTask")
    suspend fun deleteTask(
    @Body deleteRequest: DeleteRequest
    ) : Response<Status>

    @POST("storeCalendarTask")
    suspend fun createTask(
    @Body createRequest: StoreTaskRequest
    ) : Response<Status>
}