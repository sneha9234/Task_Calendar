package com.example.taskcalendar.presentation.calendar

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.taskcalendar.domain.repository.CreateTaskRepository
import com.example.taskcalendar.domain.model.request.TaskData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class CreateTaskViewModel @Inject constructor(
    private val repository: CreateTaskRepository
) : ViewModel() {

    private val coroutineExceptionHandler = CoroutineExceptionHandler { _, throwable ->
        throwable.printStackTrace()
    }

    suspend fun createTask(taskData: TaskData) {
        viewModelScope.launch(Dispatchers.IO + coroutineExceptionHandler) {
            val result = repository.createTask(taskData)
            if (result.isSuccessful) {
                if(result.body()?.status.equals("Success")){

                }
            } else {
                // result.errorBody()
            }
        }
    }
}