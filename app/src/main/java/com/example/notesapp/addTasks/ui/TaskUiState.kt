package com.example.notesapp.addTasks.ui

import com.example.notesapp.addTasks.ui.model.TaskModel

sealed interface TaskUiState {
    object Loading:TaskUiState
    data class Error(val throwable:Throwable):TaskUiState
    data class Success(val tasks:List<TaskModel>):TaskUiState
}