package com.example.notesapp.addTasks.domain.useCase

import com.example.notesapp.addTasks.data.TaskRepository
import com.example.notesapp.addTasks.ui.model.TaskModel
import javax.inject.Inject

class DeleteTaskUseCase @Inject constructor(private val taskRepository: TaskRepository) {

    suspend operator fun invoke(taskModel: TaskModel) {
        taskRepository.delete(taskModel)
    }
}