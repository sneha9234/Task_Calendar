package com.example.taskcalendar.presentation.home

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.taskcalendar.domain.repository.TaskViewAndEditRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@HiltViewModel
class TaskViewAndEditViewModel @Inject constructor(
    private val repository: TaskViewAndEditRepository
) : ViewModel() {

    private val _homeScreenState = mutableStateOf(
        HomeScreenState()
    )
    val homeScreenState: State<HomeScreenState> = _homeScreenState

    private val coroutineExceptionHandler = CoroutineExceptionHandler { _, throwable ->
        throwable.printStackTrace()
    }

    init {
        viewModelScope.launch(Dispatchers.IO + coroutineExceptionHandler) {
            refreshTasks()
        }
    }

    private suspend fun refreshTasks() {
        val result = repository.getTasksList()
        if (result.isSuccessful) {
            _homeScreenState.value = homeScreenState.value.copy(
                tasksList = result.body()
            )
        } else {
            // result.errorBody()
        }
    }

    suspend fun deleteTask(taskId: Int) {
        viewModelScope.launch(Dispatchers.IO + coroutineExceptionHandler) {
            val result = repository.deleteTask(taskId)
            if (result.isSuccessful) {
                if(result.body()?.status.equals("Success"))
                    refreshTasks()

            } else {
                // result.errorBody()
            }
        }
    }

}