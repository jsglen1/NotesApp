package com.example.notesapp.addTasks.domain

import com.example.notesapp.addTasks.data.TaskRepository
import com.example.notesapp.addTasks.ui.model.TaskModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetTasksUseCase @Inject constructor(private val taskRepository: TaskRepository) {

    operator fun invoke(): Flow<List<TaskModel>> {
        return taskRepository.tasks
    }

}