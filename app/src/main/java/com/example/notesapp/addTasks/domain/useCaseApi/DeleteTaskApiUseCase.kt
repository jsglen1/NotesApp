package com.example.notesapp.addTasks.domain.useCaseApi

import com.example.notesapp.addTasks.data.TaskRepository
import com.example.notesapp.addTasks.ui.model.TaskModel
import javax.inject.Inject

class DeleteTaskApiUseCase @Inject constructor(private val taskRepository: TaskRepository) {
    suspend operator fun invoke(taskModel: TaskModel) {
        taskRepository.deleteTaskFireBase(taskModel)
    }
}