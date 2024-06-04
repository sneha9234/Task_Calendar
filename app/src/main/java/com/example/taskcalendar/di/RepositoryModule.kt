package com.example.taskcalendar.di

import com.example.taskcalendar.data.repository.CreateTaskRepositoryImpl
import com.example.taskcalendar.domain.repository.CreateTaskRepository
import com.example.taskcalendar.data.repository.TaskViewAndEditRepositoryImpl
import com.example.taskcalendar.domain.repository.TaskViewAndEditRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    abstract fun taskViewAndEditRepository(taskViewAndEditRepositoryImpl: TaskViewAndEditRepositoryImpl): TaskViewAndEditRepository

    @Binds
    abstract fun createTaskRepository(createTaskRepositoryImpl: CreateTaskRepositoryImpl): CreateTaskRepository
}