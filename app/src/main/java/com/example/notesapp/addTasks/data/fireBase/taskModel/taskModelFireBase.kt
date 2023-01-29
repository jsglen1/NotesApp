package com.example.notesapp.addTasks.data.fireBase.taskModel

data class taskModelFireBase(
    val id: Int,
    val task: String,
    var selected: Boolean = false
)
