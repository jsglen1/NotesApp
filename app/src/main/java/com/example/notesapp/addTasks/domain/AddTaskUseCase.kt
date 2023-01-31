package com.example.notesapp.addTasks.domain

import com.example.notesapp.addTasks.data.TaskRepository
import com.example.notesapp.addTasks.ui.model.TaskModel
import javax.inject.Inject

class AddTaskUseCase @Inject constructor(private val taskRepository: TaskRepository) {

    suspend operator fun invoke(taskModel: TaskModel) {
        taskRepository.addTask(taskModel)
        taskRepository.addTaskFirebase(taskModel)
        //nueva funcion pro
    }
}