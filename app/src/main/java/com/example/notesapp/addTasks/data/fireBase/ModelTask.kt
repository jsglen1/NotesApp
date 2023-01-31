package com.example.notesapp.addTasks.data.fireBase

data class ModelTask(
    val id: Int,
    val task: String,
    var selected: Boolean = false
)