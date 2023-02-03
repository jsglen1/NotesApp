package com.example.notesapp.addTasks.domain.useCaseApi

import com.example.notesapp.addTasks.data.TaskRepository
import com.example.notesapp.addTasks.ui.model.TaskModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import javax.inject.Inject

class GetTasksApiUseCase@Inject constructor(private val taskRepository: TaskRepository) {
     suspend operator fun invoke(): Flow<List<TaskModel>> {
        return taskRepository.getTaskFireBase()
    }
}