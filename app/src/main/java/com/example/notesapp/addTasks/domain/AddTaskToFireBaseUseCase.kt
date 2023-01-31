package com.example.notesapp.addTasks.domain

import com.example.notesapp.addTasks.data.TaskRepository
import com.example.notesapp.addTasks.ui.model.TaskModel
import javax.inject.Inject


class AddTaskToFireBaseUseCase @Inject constructor(private val taskRepository: TaskRepository) {

    operator fun invoke(taskModel: TaskModel) {
        taskRepository.addTaskFirebase(taskModel)
    }
}