package com.example.notesapp.addTasks.domain

import com.example.notesapp.addTasks.data.TaskRepository
import com.example.notesapp.addTasks.ui.model.TaskModel
import javax.inject.Inject

class GetTasksToFireBaseUseCase @Inject constructor(private val taskRepository: TaskRepository) {

    operator fun invoke():List<TaskModel> =
        taskRepository.tasksList

}