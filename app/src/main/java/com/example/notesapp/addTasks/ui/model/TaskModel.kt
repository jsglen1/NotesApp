package com.example.notesapp.addTasks.ui.model

data class TaskModel(
    val id: Int,
    val task: String,
    var selected: Boolean
) {
    constructor(task: String) : this(id = System.currentTimeMillis().hashCode(), task = "", selected = false)
}